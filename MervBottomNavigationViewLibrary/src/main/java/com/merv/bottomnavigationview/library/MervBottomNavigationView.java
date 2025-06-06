package com.merv.bottomnavigationview.library;

import androidx.core.content.res.ResourcesCompat;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;


import com.merv.bottomnavigationview.library.Interpolar.MervInterpolator;
import com.merv.bottomnavigationview.library.Listener.OnNavItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2025 b3ddo dEV' (github.com/b3ddodev)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 **/


public class MervBottomNavigationView extends View {

    /**
     * Alt gezinme çubuğunun arka planının şekli için kullanılan Path objesi.
     */
    private final Path mBottomNavBackgroundPath = new Path();

    /**
     * Arka plan çizimi için kullanılan Paint nesnesi.
     */
    private Paint mBottomNavBackgroundPaint;

    /**
     * Seçili daireyi çizmek için kullanılan Paint nesnesi.
     */
    private Paint mBottomNavCirclePaint;

    /**
     * Menü yazılarını çizmek için kullanılan Paint nesnesi.
     */
    private Paint mBottomNavTextPaint;

    /**
     * Daire animasyonunu kontrol eden ValueAnimator nesnesi.
     */
    private ValueAnimator mCircleAnimator;

    /**
     * Alt gezinme çubuğunun arka plan rengi.
     * Varsayılan olarak beyaz (Color.WHITE).
     */
    private int mBottomNavMorphBackgroundColor = Color.WHITE;

    /**
     * Alt gezinme çubuğu arka planının gölge katmanının boyutu (piksel cinsinden).
     */
    private float mBottomNavMorphBackgroundShadowLayerSize = 10f;

    /**
     * Seçili dairenin rengi.
     * Varsayılan turuncu ton (#FF7043).
     */
    private int mBottomNavCircleColor = Color.parseColor("#FF7043");

    /**
     * Seçili dairenin gölge katmanının boyutu (piksel cinsinden).
     */
    private float mBottomNavCircleShadowLayerSize = 10f;

    /**
     * Seçili ikonların rengi.
     * Varsayılan beyaz (Color.WHITE).
     */
    private int mBottomNavSelectedIconColor = Color.WHITE;

    /**
     * Seçilmeyen ikonların rengi.
     * Varsayılan turuncu ton (#FF7043).
     */
    private int mBottomNavUnselectedIconColor = Color.parseColor("#FF7043");

    /**
     * Seçili ikonların boyutu (piksel cinsinden).
     */
    private float mBottomNavSelectedIconSize = 36f;

    /**
     * Seçilmeyen ikonların boyutu (piksel cinsinden).
     */
    private float mBottomNavUnselectedIconSize = 24f;

    /**
     * Menü metinlerinin rengi.
     * Varsayılan siyah (Color.BLACK).
     */
    private int mBottomNavTextColor = Color.BLACK;

    /**
     * Menü metinlerinin boyutu (piksel cinsinden).
     */
    private float mBottomNavTextSize = 14f;

    /**
     * Menü metinlerinin kalın (bold) görünmesini sağlayan boolean.
     * false ise normal yazı tipi olur.
     */
    private boolean mBottomNavTextFakeBold = false;

    /**
     * Menü metinleri için kullanılan yazı tipi (Typeface).
     * Null ise varsayılan yazı tipi kullanılır.
     */
    private Typeface mBottomNavTextChooseFont = null;

    /**
     * Menü metinlerinin üstten boşluk mesafesi (piksel cinsinden).
     */
    private float mBottomNavTextMarginTop = 10f;

    /**
     * Menü metni animasyonu sırasında metnin hareket edeceği mesafe (piksel cinsinden).
     */
    private float mBottomNavTextAnimDistancePX;

    /**
     * Menüdeki maksimum öğe sayısı.
     * Şimdilik 3 olarak sabitlenmiştir.
     */
    private static final int MAX_ITEMS_SIZE = 5;

    /**
     * Menü öğelerinin ikonlarını tutan Drawable dizisi.
     */
    private final Drawable[] mBottomNavItemIcons = new Drawable[MAX_ITEMS_SIZE];

    /**
     * Menü öğelerinin başlıklarını tutan String dizisi.
     */
    private final String[] mBottomNavItemTitles = new String[MAX_ITEMS_SIZE];

    /**
     * Menü öğelerinin ikon boyutlarını tutan float dizisi.
     */
    private final float[] mBottomNavItemIconSize = new float[MAX_ITEMS_SIZE];

    /**
     * Menü metinlerinin saydamlık (alpha) değerlerini tutan float dizisi.
     * Değer aralığı 0.0f (tam saydam) ile 1.0f (tam görünür) arasındadır.
     */
    private final float[] mBottomNavItemTextAlphas = new float[MAX_ITEMS_SIZE];

    /**
     * Menü metinlerinin animasyon için yatay veya dikey offset değerlerini tutan float dizisi.
     */
    private final float[] mBottomNavItemTextOffsets = new float[MAX_ITEMS_SIZE];

    /**
     * Menüde şu anda kaç tane öğe olduğu bilgisini tutar.
     */
    private int mBottomNavItemCount = 0;

    /**
     * Menü kaynak dosyasının (menu XML) resource ID'si.
     */
    private int mBottomNavMenuRes;

    /**
     * Şu anda seçili olan menü öğesinin indeksi (0 tabanlı).
     */
    private int mBottomNavSelectedIndex = 0;

    /**
     * Animasyon interpolatör tipi.
     * Varsayılan olarak 4 (OVERSHOOT).
     */
    private int mBottomNavAnimationInterpolatorType = 4;

    /**
     * Menü öğesi animasyon süresi (milisaniye cinsinden).
     */
    private int mBottomNavItemAnimDuration = 400;

    /**
     * Görüntü bileşeninin genişlik ve yükseklik değerleri (piksel cinsinden).
     */
    private int mViewWidth, mViewHeight;

    /**
     * Alt gezinme çubuğunun üst Y koordinatı ve yüksekliği (piksel cinsinden).
     */
    private float mBarTopY, mBarHeight;

    /**
     * Seçili dairenin yarıçapı ve Y koordinatı merkezi (piksel cinsinden).
     */
    private float mCircleRadius, mCircleCenterY;

    /**
     * Animasyonun merkezi X koordinatı (daire hareketi için, piksel cinsinden).
     */
    private float mAnimCenterX;

    /**
     * Menü öğesi seçildiğinde tetiklenen dinleyici arayüzü.
     */
    private OnNavItemSelectedListener mListener;


    public MervBottomNavigationView(Context mContext) {
        super(mContext);
        MervNavigationViewController(null);
    }

    public MervBottomNavigationView(Context mContext, @Nullable AttributeSet mAttributeSet) {
        super(mContext, mAttributeSet);
        MervNavigationViewController(mAttributeSet);
    }

    public MervBottomNavigationView(Context mContext, @Nullable AttributeSet mAttributeSet, int mDefStyleAttr) {
        super(mContext, mAttributeSet, mDefStyleAttr);
        MervNavigationViewController(mAttributeSet);
    }

    /**
     * MervNavigationViewController metodu:
     * <p>
     * Bu metodun görevi, XML layout dosyasından gelen özel attribute değerlerini okumak,
     * bunları cihazın ekran yoğunluğuna göre uygun piksel (px) değerlerine çevirmek
     * ve alt navigasyon görünümünün temel grafik ve stil ayarlarını başlatmaktır.
     *
     * @param mAttributeSet XML layout'tan gelen attribute seti, özel stil tanımları içerir.
     *                      Eğer null ise varsayılan değerler kullanılır.
     *                      <p>
     *                      Detaylar:
     *                      1. DisplayMetrics kullanılarak dp ve sp birimleri, cihazın yoğunluğuna göre
     *                      piksele dönüştürülür. Bu sayede farklı cihazlarda tutarlı boyutlar sağlanır.
     *                      <p>
     *                      2. Eğer XML üzerinden AttributeSet sağlanmışsa, TypedArray ile
     *                      tanımlanan tüm özel attribute değerleri okunur. Bu attribute’lar:
     *                      <p>
     *                      - Arka plan rengi ve gölge büyüklüğü
     *                      - Seçili dairenin rengi ve gölge büyüklüğü
     *                      - Seçili ve seçilmeyen ikonların renkleri ve boyutları
     *                      - Menü metinlerinin rengi, boyutu, kalınlığı ve yazı tipi
     *                      - Menü metinlerinin üstten boşluk mesafesi
     *                      - Animasyon interpolatör tipi ve süresi
     *                      - Başlangıçta seçili olacak menü öğesi indeksi
     *                      - Menü kaynağı (menu XML dosyası)
     *                      <p>
     *                      Bunlar alınır, eğer XML’de tanımlı değilse varsayılan ön tanımlı değerler kullanılır.
     *                      <p>
     *                      3. Yazı tipi atanırken kaynak ID’si geçerli değilse veya yüklenemezse
     *                      uygun bir hata fırlatılır ve default yazı tipi kullanılır.
     *                      <p>
     *                      4. Paint nesneleri (mBottomNavBackgroundPaint, mBottomNavCirclePaint, mBottomNavTextPaint)
     *                      anti-aliasing (kenar yumuşatma) ile oluşturulur ve ilgili renk, gölge,
     *                      yazı tipi, yazı boyutu gibi stil özellikleri atanır.
     *                      <p>
     *                      5. Menü öğelerinin ikon, başlık, ikon boyutu, metin alfa ve offset değerleri
     *                      için başlangıç dizileri sıfırlanır ve default değerlerle doldurulur.
     *                      <p>
     *                      6. Eğer View edit modunda ise (Android Studio ön izleme gibi),
     *                      örnek ikon ve başlıklarla üç adet ön izleme öğesi eklenir,
     *                      bu sayede tasarımda ön izleme görseli sağlanır.
     *                      <p>
     *                      7. Menü kaynağı belirtilmişse ilgili menü XML dosyasından
     *                      öğeler okunup menü oluşturulur.
     *                      <p>
     *                      8. Gölge efektlerinin düzgün çalışması için View katman tipi
     *                      yazılım katmanı (LAYER_TYPE_SOFTWARE) olarak ayarlanır.
     *                      <p>
     *                      Böylece, alt navigasyon çubuğu görsel ve davranış olarak
     *                      tamamen tanımlanmış ve kullanıma hazır hale getirilir.
     */

    private void MervNavigationViewController(@Nullable AttributeSet mAttributeSet) {
        DisplayMetrics mDisplayMetrics = getResources().getDisplayMetrics();
        float mDefaultBottomNavBackgroundElevationPx =
                mBottomNavMorphBackgroundShadowLayerSize * mDisplayMetrics.density;
        float mDefaultBottomNavCircleShadowElevationPx =
                mBottomNavCircleShadowLayerSize * mDisplayMetrics.density;
        float mDefaultBottomNavSelectedIconSizePx =
                mBottomNavSelectedIconSize * mDisplayMetrics.density;
        float mDefaultBottomNavUnselectedIconSizePx =
                mBottomNavUnselectedIconSize * mDisplayMetrics.density;
        float mDefaultBottomNavTextSizePx =
                mBottomNavTextSize * mDisplayMetrics.scaledDensity;
        float mDefaultBottomNavTextMarginTopPx =
                mBottomNavTextMarginTop * mDisplayMetrics.density;

        mBottomNavTextAnimDistancePX = dpToPx(20f);

        if (mAttributeSet != null) {
            TypedArray mTypedArray = getContext().obtainStyledAttributes(
                    mAttributeSet,
                    R.styleable.MervBottomNavigationView
            );

            mBottomNavMorphBackgroundColor = mTypedArray.getColor(
                    R.styleable.MervBottomNavigationView_mBottomNavMorphBackgroundColor,
                    Color.WHITE
            );
            mBottomNavMorphBackgroundShadowLayerSize = mTypedArray.getDimension(
                    R.styleable.MervBottomNavigationView_mBottomNavMorphBackgroundShadowLayerSize,
                    mDefaultBottomNavBackgroundElevationPx
            );
            mBottomNavCircleColor = mTypedArray.getColor(
                    R.styleable.MervBottomNavigationView_mBottomNavCircleColor,
                    Color.parseColor("#FF7043")
            );
            mBottomNavCircleShadowLayerSize = mTypedArray.getDimension(
                    R.styleable.MervBottomNavigationView_mBottomNavCircleShadowLayerSize,
                    mDefaultBottomNavCircleShadowElevationPx
            );
            mBottomNavSelectedIconColor = mTypedArray.getColor(
                    R.styleable.MervBottomNavigationView_mBottomNavSelectedIconColor,
                    Color.WHITE
            );
            mBottomNavUnselectedIconColor = mTypedArray.getColor(
                    R.styleable.MervBottomNavigationView_mBottomNavUnselectedIconColor,
                    Color.parseColor("#FF7043")
            );
            mBottomNavSelectedIconSize = mTypedArray.getDimension(
                    R.styleable.MervBottomNavigationView_mBottomNavSelectedIconSize,
                    mDefaultBottomNavSelectedIconSizePx
            );
            mBottomNavUnselectedIconSize = mTypedArray.getDimension(
                    R.styleable.MervBottomNavigationView_mBottomNavUnselectedIconSize,
                    mDefaultBottomNavUnselectedIconSizePx
            );
            mBottomNavTextColor = mTypedArray.getColor(
                    R.styleable.MervBottomNavigationView_mBottomNavTextColor,
                    Color.BLACK
            );
            mBottomNavTextSize = mTypedArray.getDimension(
                    R.styleable.MervBottomNavigationView_mBottomNavTextSize,
                    mDefaultBottomNavTextSizePx
            );
            mBottomNavTextFakeBold = mTypedArray.getBoolean(
                    R.styleable.MervBottomNavigationView_mBottomNavTextFakeBold,
                    true
            );
            int mTextChooseFontResourcesID = mTypedArray.getResourceId(
                    R.styleable.MervBottomNavigationView_mBottomNavTextChooseFont,
                    0
            );
            if (mTextChooseFontResourcesID != 0) {
                try {
                    Typeface tmpFont = androidx.core.content.res.ResourcesCompat.getFont(
                            getContext(),
                            mTextChooseFontResourcesID
                    );
                    if (tmpFont != null) {
                        mBottomNavTextChooseFont = tmpFont;
                    } else {
                        mBottomNavTextChooseFont = Typeface.DEFAULT;
                    }
                } catch (Exception mException) {
                    Log.e("MervBottomNavigationViewLog", "Geçersiz yazı tipi kaynağı: " + mException.getMessage());
                    mBottomNavTextChooseFont = Typeface.DEFAULT;
                }
            } else {
                mBottomNavTextChooseFont = Typeface.DEFAULT;
            }
            mBottomNavTextMarginTop = mTypedArray.getDimension(
                    R.styleable.MervBottomNavigationView_mBottomNavTextMarginTop,
                    mDefaultBottomNavTextMarginTopPx
            );
            mBottomNavAnimationInterpolatorType = mTypedArray.getInt(
                    R.styleable.MervBottomNavigationView_mBottomNavAnimationInterpolator,
                    mBottomNavAnimationInterpolatorType
            );
            mBottomNavSelectedIndex = mTypedArray.getInt(
                    R.styleable.MervBottomNavigationView_mBottomNavSelectedIndex,
                    mBottomNavSelectedIndex
            );
            mBottomNavItemAnimDuration = mTypedArray.getInt(
                    R.styleable.MervBottomNavigationView_mBottomNavItemAnimationDuration,
                    mBottomNavItemAnimDuration
            );
            mBottomNavMenuRes = mTypedArray.getResourceId(
                    R.styleable.MervBottomNavigationView_mBottomNavMenuResource,
                    mBottomNavMenuRes
            );

            mTypedArray.recycle();
        }

        mBottomNavBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomNavBackgroundPaint.setColor(mBottomNavMorphBackgroundColor);
        mBottomNavBackgroundPaint.setStyle(Paint.Style.FILL);
        mBottomNavBackgroundPaint.setShadowLayer(
                mBottomNavMorphBackgroundShadowLayerSize,
                0,
                0,
                Color.parseColor("#33000000")
        );

        mBottomNavCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomNavCirclePaint.setColor(mBottomNavCircleColor);
        mBottomNavCirclePaint.setShadowLayer(
                mBottomNavCircleShadowLayerSize,
                0,
                0,
                Color.parseColor("#33000000")
        );

        mBottomNavTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomNavTextPaint.setColor(mBottomNavTextColor);
        mBottomNavTextPaint.setTextSize(mBottomNavTextSize);
        mBottomNavTextPaint.setTypeface(mBottomNavTextChooseFont);
        mBottomNavTextPaint.setTextAlign(Paint.Align.CENTER);
        mBottomNavTextPaint.setFakeBoldText(mBottomNavTextFakeBold);

        for (int mI = 0; mI < MAX_ITEMS_SIZE; mI++) {
            mBottomNavItemIcons[mI] = null;
            mBottomNavItemTitles[mI] = null;
            mBottomNavItemIconSize[mI] = mBottomNavUnselectedIconSize;
            mBottomNavItemTextAlphas[mI] = 0f;
            mBottomNavItemTextOffsets[mI] = mBottomNavTextAnimDistancePX;
        }
        mBottomNavItemCount = 0;
        mAnimCenterX = -1;

        if (isInEditMode() && mBottomNavMenuRes == 0) {
            setAddItem(
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_preview_home),
                    "Home"
            );
            setAddItem(
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_preview_search),
                    "Search"
            );
            setAddItem(
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_preview_add),
                    "Add"
            );
            setAddItem(
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_preview_reaction),
                    "Reaction"
            );
            setAddItem(
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_preview_favorite),
                    "Favorite"
            );
            setBottomNavSelectedIndex(2);
        }
        if (mBottomNavMenuRes != 0) {
            setMenu(mBottomNavMenuRes);
        }
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    /**
     * Alt navigasyonun arka plan rengini ayarlar.
     *
     * @param mColor Yeni arka plan rengi (Color int formatında)
     */
    public void setBottomNavBackgroundColor(int mColor) {
        mBottomNavMorphBackgroundColor = mColor;
        mBottomNavBackgroundPaint.setColor(mColor);
        invalidate();
    }

    /**
     * Alt navigasyonun arka plan gölge yüksekliğini (elevation) ayarlar.
     *
     * @param mElevationPx Gölge yüksekliği (pixel cinsinden)
     */
    public void setBottomNavBackgroundElevation(float mElevationPx) {
        mBottomNavMorphBackgroundShadowLayerSize = mElevationPx;
        setElevation(mElevationPx);
        invalidate();
    }

    /**
     * Seçili dairenin rengini ayarlar.
     *
     * @param mColor Yeni daire rengi (Color int formatında)
     */
    public void setBottomNavCircleColor(int mColor) {
        mBottomNavCircleColor = mColor;
        mBottomNavCirclePaint.setColor(mColor);
        invalidate();
    }

    /**
     * Seçili dairenin gölge yüksekliğini (elevation) ayarlar.
     *
     * @param mElevationPx Gölge yüksekliği (pixel cinsinden)
     */
    public void setBottomNavCircleShadowElevation(float mElevationPx) {
        mBottomNavCircleShadowLayerSize = mElevationPx;
        mBottomNavCirclePaint.setShadowLayer(
                mElevationPx,
                0,
                0,
                Color.parseColor("#33000000")
        );
        invalidate();
    }

    /**
     * Seçili ikonların rengini ayarlar.
     *
     * @param mColor Yeni seçili ikon rengi (Color int formatında)
     */
    public void setBottomNavSelectedIconColor(int mColor) {
        mBottomNavSelectedIconColor = mColor;
        invalidate();
    }

    /**
     * Seçilmeyen ikonların rengini ayarlar.
     *
     * @param mColor Yeni seçilmeyen ikon rengi (Color int formatında)
     */
    public void setBottomNavUnselectedIconColor(int mColor) {
        mBottomNavUnselectedIconColor = mColor;
        invalidate();
    }

    /**
     * Seçili ikonların boyutunu ayarlar.
     *
     * @param mSizePx Yeni seçili ikon boyutu (pixel cinsinden)
     */
    public void setBottomNavSelectedIconSize(float mSizePx) {
        mBottomNavSelectedIconSize = mSizePx;
        requestLayout();
        invalidate();
    }

    /**
     * Seçilmeyen ikonların boyutunu ayarlar.
     *
     * @param mSizePx Yeni seçilmeyen ikon boyutu (pixel cinsinden)
     */
    public void setBottomNavUnselectedIconSize(float mSizePx) {
        mBottomNavUnselectedIconSize = mSizePx;
        requestLayout();
        invalidate();
    }

    /**
     * Menü metinlerinin rengini ayarlar.
     *
     * @param mColor Yeni metin rengi (Color int formatında)
     */
    public void setBottomNavTextColor(int mColor) {
        mBottomNavTextColor = mColor;
        mBottomNavTextPaint.setColor(mColor);
        invalidate();
    }

    /**
     * Menü metinlerinin boyutunu ayarlar.
     *
     * @param mTextSizePx Yeni metin boyutu (pixel cinsinden)
     */
    public void setBottomNavTextSize(float mTextSizePx) {
        mBottomNavTextSize = mTextSizePx;
        mBottomNavTextPaint.setTextSize(mTextSizePx);
        requestLayout();
        invalidate();
    }

    /**
     * Menü metinlerinin kalın (bold) görünmesini sağlar veya kaldırır.
     *
     * @param mIsFakeBold true ise kalın, false ise normal yazı tipi olur.
     */
    public void setBottomNavTextFakeBold(boolean mIsFakeBold) {
        mBottomNavTextFakeBold = mIsFakeBold;
        mBottomNavTextPaint.setFakeBoldText(mIsFakeBold);
        invalidate();
    }

    /**
     * Menü metinlerinin yazı tipini ayarlar.
     *
     * @param mTypeface Yeni Typeface objesi (yazı tipi)
     */
    public void setBottomNavTextFont(Typeface mTypeface) {
        mBottomNavTextChooseFont = mTypeface;
        mBottomNavTextPaint.setTypeface(mTypeface);
        invalidate();
    }

    /**
     * Menü metinlerinin üstten boşluk mesafesini ayarlar.
     *
     * @param mMarginPx Üstten boşluk mesafesi (pixel cinsinden)
     */
    public void setBottomNavTextMarginTop(float mMarginPx) {
        mBottomNavTextMarginTop = mMarginPx;
        requestLayout();
        invalidate();
    }

    /**
     * Animasyon interpolatör tipini ayarlar.
     *
     * @param mType Animasyon interpolatör tipi (enum int)
     */
    public void setBottomNavAnimationInterpolator(int mType) {
        mBottomNavAnimationInterpolatorType = mType;
        if (mCircleAnimator != null) {
            mCircleAnimator.setInterpolator(MervInterpolator.get(mType, getContext()));
        }
    }

    /**
     * Seçili menü öğesi indeksini ayarlar.
     *
     * @param mIndex Seçili olacak menü öğesinin 0 tabanlı indeksi
     */
    public void setBottomNavSelectedIndex(int mIndex) {
        if (mIndex < 0 || mIndex >= mBottomNavItemCount) return;
        for (int mI = 0; mI < MAX_ITEMS_SIZE; mI++) {
            mBottomNavItemTextAlphas[mI] = 0f;
            mBottomNavItemTextOffsets[mI] = mBottomNavTextAnimDistancePX;
        }
        AnimationSelectionToIndex(mIndex);
    }

    /**
     * Menü öğesi animasyon süresini ayarlar.
     *
     * @param mDurationMs Animasyon süresi (milisaniye)
     */
    public void setBottomNavItemAnimationDuration(int mDurationMs) {
        mBottomNavItemAnimDuration = mDurationMs;
    }


    /**
     * Menü kaynağını (menu XML dosyasını) yükler ve menü öğelerini hazırlar.
     *
     * @param mMenuResourcesID Menü kaynak dosyasının resource ID'si.
     *                         Geçerli, sıfırdan farklı bir ID olmalıdır.
     * @throws IllegalArgumentException Eğer verilen menu resource ID 0 ise fırlatılır.
     * @throws IllegalStateException    Eğer önceden manuel öğeler eklenmişse,
     *                                  ya da menü boşsa ya da çok fazla öğe varsa,
     *                                  ya da öğelerde ikon veya başlık eksikse fırlatılır.
     *                                  <p>
     *                                  Detaylar:
     *                                  - Verilen menü resource dosyası PopupMenu kullanılarak şişirilir.
     *                                  - Menüdeki öğelerin sayısı MAX_ITEMS_SIZE'ı geçmemelidir.
     *                                  - Her menü öğesinin bir ikonu ve başlığı olmalıdır.
     *                                  - Seçili öğe indeksi geçersizse sıfırlanır.
     *                                  - Öğelerin ikonları, başlıkları, boyutları ve animasyon alfa/offset değerleri
     *                                  uygun şekilde ayarlanır.
     *                                  - Menü öğesi sayısından fazla olan indexlerdeki öğeler sıfırlanır.
     *                                  - Son olarak görünüm yeniden çizilir ve düzen talebi gönderilir.
     */
    public void setMenu(int mMenuResourcesID) {
        if (mMenuResourcesID == 0) {
            throw new IllegalArgumentException(
                    "Menü kaynak kimliği 0 olamaz. Lütfen geçerli bir menü kaynağı sağlayın."
            );
        }

        if (mBottomNavItemCount > 0 && mBottomNavItemIcons[0] != null) {
            throw new IllegalStateException(
                    "Manuel olarak menü öğesi eklenmiş. " +
                            "Lütfen önce clearItems() ile manuel öğeleri temizleyin ve ardından setMenu() çağırın."
            );
        }

        PopupMenu mPopupMenu = new PopupMenu(getContext(), this);
        mPopupMenu.getMenuInflater().inflate(mMenuResourcesID, mPopupMenu.getMenu());
        android.view.Menu mMenu = mPopupMenu.getMenu();

        int rawCount = mMenu.size();
        if (rawCount == 0) {
            throw new IllegalStateException(
                    "Menü kaynağında hiçbir öğe yok. En az bir öğe gerekli."
            );
        }

        List<Drawable> validIcons = new ArrayList<>();
        List<String> validTitles = new ArrayList<>();

        for (int i = 0; i < rawCount; i++) {
            android.view.MenuItem mItem = mMenu.getItem(i);
            Drawable mIcon = mItem.getIcon();
            CharSequence mTitle = mItem.getTitle();
            if (mIcon == null || mTitle == null || mTitle.toString().trim().isEmpty()) {
                Log.w("MervBNV", "Menü öğesi " + i + " geçersiz. Atlanıyor.");
                continue;
            }
            validIcons.add(mIcon);
            validTitles.add(mTitle.toString());
        }

        int mCount = validIcons.size();
        if (mCount == 0) {
            throw new IllegalStateException(
                    "Geçerli hiçbir menü öğesi bulunamadı. Lütfen en az bir tane ikon+başlık içeren öğe ekleyin."
            );
        }
        if (mCount > MAX_ITEMS_SIZE) {
            throw new IllegalStateException(
                    "Menü kaynağında çok fazla geçerli öğe var (" + mCount + "). İzin verilen maksimum değer "
                            + MAX_ITEMS_SIZE
            );
        }

        mBottomNavItemCount = mCount;
        if (mBottomNavSelectedIndex < 0 || mBottomNavSelectedIndex >= mCount) {
            mBottomNavSelectedIndex = 0;
        }

        for (int mI = 0; mI < mCount; mI++) {
            mBottomNavItemIcons[mI] = validIcons.get(mI);
            mBottomNavItemTitles[mI] = validTitles.get(mI);
            if (mI == mBottomNavSelectedIndex) {
                mBottomNavItemIconSize[mI] = mBottomNavSelectedIconSize;
                mBottomNavItemTextAlphas[mI] = 1f;
                mBottomNavItemTextOffsets[mI] = 0f;
            } else {
                mBottomNavItemIconSize[mI] = mBottomNavUnselectedIconSize;
                mBottomNavItemTextAlphas[mI] = 0f;
                mBottomNavItemTextOffsets[mI] = mBottomNavTextAnimDistancePX;
            }
        }

        for (int mI = mCount; mI < MAX_ITEMS_SIZE; mI++) {
            mBottomNavItemIcons[mI] = null;
            mBottomNavItemTitles[mI] = null;
            mBottomNavItemIconSize[mI] = mBottomNavUnselectedIconSize;
            mBottomNavItemTextAlphas[mI] = 0f;
            mBottomNavItemTextOffsets[mI] = mBottomNavTextAnimDistancePX;
        }

        mAnimCenterX = ComputeCenterXForIndex(mBottomNavSelectedIndex);
        requestLayout();
        invalidate();
    }

    /**
     * Menüye manuel olarak yeni bir öğe ekler.
     *
     * @param mIcon  Eklenecek öğenin ikonu (Drawable).
     * @param mTitle Eklenecek öğenin başlığı (String).
     * @throws IllegalStateException Eğer bileşene daha önce menü atanmışsa (XML veya kod ile),
     *                               manuel öğe eklenmesine izin verilmez.
     * @throws IllegalStateException Eğer maksimum öğe sayısı (MAX_ITEMS_SIZE) aşılırsa fırlatılır.
     *                               <p>
     *                               Detaylar:
     *                               - Menü daha önce setMenu() ile atanmışsa, manuel ekleme engellenir.
     *                               - Maksimum öğe sayısı aşılırsa hata verir.
     *                               - Yeni öğe dizilere eklenir ve seçili indekse göre ikon boyutu,
     *                               metin saydamlığı ve animasyon offset değerleri ayarlanır.
     *                               - Eğer eklenen ilk öğe ise, o otomatik seçili yapılır.
     *                               - Son olarak görünüm yeniden düzenlenir ve çizim yenilenir.
     */
    public void setAddItem(Drawable mIcon, String mTitle) {
        if (mBottomNavMenuRes != 0) {
            throw new IllegalStateException(
                    "Bu bileşene daha önce XML veya kod ile menü atanmış. "
                            + "Lütfen önce clearMenu() çağırarak menüyü temizleyin, ardından manuel item ekleyin."
            );
        }

        if (mBottomNavItemCount >= MAX_ITEMS_SIZE) {
            throw new IllegalStateException(
                    "Şundan daha fazlası eklenemez: " + MAX_ITEMS_SIZE
                            + " öğe. BottomNavigationView'a bu kadar izin veriliyor :)"
            );
        }

        mBottomNavItemIcons[mBottomNavItemCount] = mIcon;
        mBottomNavItemTitles[mBottomNavItemCount] = mTitle;

        if (mBottomNavItemCount == mBottomNavSelectedIndex) {
            mBottomNavItemIconSize[mBottomNavItemCount] = mBottomNavSelectedIconSize;
            mBottomNavItemTextAlphas[mBottomNavItemCount] = 1f;
            mBottomNavItemTextOffsets[mBottomNavItemCount] = 0f;
        } else {
            mBottomNavItemIconSize[mBottomNavItemCount] = mBottomNavUnselectedIconSize;
            mBottomNavItemTextAlphas[mBottomNavItemCount] = 0f;
            mBottomNavItemTextOffsets[mBottomNavItemCount] = mBottomNavTextAnimDistancePX;
        }

        mBottomNavItemCount++;

        if (mBottomNavItemCount == 1) {
            mBottomNavSelectedIndex = 0;
            mBottomNavItemIconSize[0] = mBottomNavSelectedIconSize;
            mBottomNavItemTextAlphas[0] = 1f;
            mBottomNavItemTextOffsets[0] = 0f;
        }

        requestLayout();
        invalidate();
    }


    /**
     * Alt navigasyon menü öğesi seçildiğinde çağrılacak listener'ı ayarlar.
     *
     * @param mListener Seçim olaylarını dinleyecek OnNavItemSelectedListener arayüzü.
     */
    public void setOnBottomNavItemSelectedListener(OnNavItemSelectedListener mListener) {
        this.mListener = mListener;
    }

    /**
     * Görünümün ölçüm aşamasında çağrılır.
     * <p>
     * Menü çubuğunun genişlik ve yüksekliğini hesaplar ve ayarlar.
     *
     * @param mWidthMeasureSpec  Genişlik ölçüm spesifikasyonu.
     * @param mHeightMeasureSpec Yükseklik ölçüm spesifikasyonu.
     */
    @Override
    protected void onMeasure(int mWidthMeasureSpec, int mHeightMeasureSpec) {
        int mWidth = MeasureSpec.getSize(mWidthMeasureSpec);

        float mCirclePadding = dpToPx(8f);
        mCircleRadius = (mBottomNavSelectedIconSize / 2f) + mCirclePadding;

        float mBumpExtra = dpToPx(4f);
        float mBumpRadius = mCircleRadius + mBumpExtra;

        float mMaxIconSize = Math.max(mBottomNavSelectedIconSize, mBottomNavUnselectedIconSize);
        float mBarPadding = dpToPx(10f);
        mBarHeight = mMaxIconSize + mBottomNavTextMarginTop + mBottomNavTextSize + mBarPadding;

        float mTopBarPadding = dpToPx(12f);

        int rawDesiredHeight = (int) Math.ceil(mBumpRadius + mBarHeight + mTopBarPadding);

        int minHeightPx = (int) dpToPx(48f);
        int mDesiredHeight = Math.max(rawDesiredHeight, minHeightPx);

        int mHeight = resolveSize(mDesiredHeight, mHeightMeasureSpec);

        mViewWidth = mWidth;
        mViewHeight = mHeight;
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    /**
     * Görünümün boyutu değiştiğinde çağrılır.
     * <p>
     * Yeni genişlik ve yükseklik değerlerini alır ve bileşenin
     * içsel ölçümlerini ve konumlandırmalarını günceller.
     * <p>
     * - Seçili dairenin yarıçapı ve konumu hesaplanır.
     * - Üst barın konumu ayarlanır.
     * - Animasyonun merkez X koordinatı ilk kez ayarlanırsa belirlenir.
     *
     * @param mNewWidth  Yeni genişlik (piksel)
     * @param mNewHeight Yeni yükseklik (piksel)
     * @param mOldWidth  Önceki genişlik (piksel)
     * @param mOldHeight Önceki yükseklik (piksel)
     */
    @Override
    protected void onSizeChanged(
            int mNewWidth,
            int mNewHeight,
            int mOldWidth,
            int mOldHeight
    ) {
        mViewWidth = mNewWidth;
        mViewHeight = mNewHeight;

        float mCirclePadding = dpToPx(8f);
        mCircleRadius = (mBottomNavSelectedIconSize / 2f) + mCirclePadding;

        float mBumpExtra = dpToPx(4f);
        float mBumpTotalRadius = mCircleRadius + mBumpExtra;

        float mTopBarPadding = dpToPx(12f);

        mBarTopY = mBumpTotalRadius + mTopBarPadding;
        mCircleCenterY = mBumpTotalRadius + mTopBarPadding;

        if (mBottomNavItemCount > 0) {
            mAnimCenterX = ComputeCenterXForIndex(mBottomNavSelectedIndex);
        } else {
            mAnimCenterX = mViewWidth / 2f;
        }
        super.onSizeChanged(mNewWidth, mNewHeight, mOldWidth, mOldHeight);
    }

    /**
     * Görünümün çizim işlemi için çağrılan metod.
     * <p>
     * Canvas üzerinde alt gezinme çubuğunun arka plan çıkıntısı,
     * seçili daire ve menü öğeleri çizilir.
     * <p>
     * - Eğer menü öğesi yoksa çizim yapılmaz.
     * - Arka plan çıkıntısı (bump) çizilir.
     * - Seçili öğeyi temsil eden daire çizilir.
     * - Menü öğeleri (ikon ve metin) çizilir.
     *
     * @param mCanvas Çizimin yapıldığı Canvas nesnesi.
     */
    @Override
    protected void onDraw(@NonNull Canvas mCanvas) {
        super.onDraw(mCanvas);
        if (mBottomNavItemCount == 0) return;

        DrawBackgroundBump(mCanvas);
        mCanvas.drawCircle(
                mAnimCenterX,
                mCircleCenterY,
                mCircleRadius,
                mBottomNavCirclePaint
        );
        DrawItems(mCanvas);
    }

    /**
     * Alt gezinme çubuğunun arka planında bulunan "bump" (çıkıntı) şekli çizilir.
     * <p>
     * Bu çıkıntı, seçili dairenin (aktif menü öğesinin) olduğu kısmı vurgulamak için
     * alt barın üst kısmında kubik Bezier eğrileri ile oluşturulur.
     * <p>
     * Metot:
     * - Önce mevcut Path sıfırlanır.
     * - Çıkıntının sol ve sağ sınırları, yarıçap ve ekstra boşluk baz alınarak hesaplanır.
     * - Bu sınırlar görünümün genişliğine göre sınırlandırılır.
     * - Path, sol uçtan başlayıp çıkıntı şeklini kubik eğrilerle çizerek sağ uca kadar gider.
     * - Sonra alt barın geri kalan alanı ve alt kısımlar kapatılır.
     * - Son olarak hazırlanan Path, arka plan Paint'i ile Canvas'a çizilir.
     *
     * @param mCanvas Çizimin yapılacağı Canvas nesnesi.
     */
    private void DrawBackgroundBump(Canvas mCanvas) {
        mBottomNavBackgroundPath.reset();

        float mViewWidthPx = mViewWidth;
        float mViewHeightPx = mViewHeight;
        float mBarTopYPx = mBarTopY;
        float mCircleRadiusPx = mCircleRadius;

        if (mBottomNavItemCount > 0) {
            if (mAnimCenterX <= 0f || Float.isNaN(mAnimCenterX)) {
                mAnimCenterX = ComputeCenterXForIndex(mBottomNavSelectedIndex);
            }
        } else {
            mAnimCenterX = mViewWidthPx / 2f;
        }

        float mCircleCenterXPx = mAnimCenterX;

        float mBumpExtraPx = dpToPx(4f);
        float mBumpTotalRadiusPx = mCircleRadiusPx + mBumpExtraPx;

        float mBumpLeftX = mCircleCenterXPx - mBumpTotalRadiusPx * 1.7f;
        float mBumpRightX = mCircleCenterXPx + mBumpTotalRadiusPx * 1.7f;
        mBottomNavBackgroundPath.moveTo(0f, mBarTopYPx);
        mBottomNavBackgroundPath.lineTo(mBumpLeftX, mBarTopYPx);

        mBottomNavBackgroundPath.cubicTo(
                // 1. kontrol noktası (yaklaşık bumpLeftX + 0.95*Radius, barTopY)
                mBumpLeftX + mBumpTotalRadiusPx * 0.95f,
                mBarTopYPx,
                // 2. kontrol noktası (circleCenterX - Radius, barTopY - Radius*0.95)
                mCircleCenterXPx - mBumpTotalRadiusPx,
                mBarTopYPx - mBumpTotalRadiusPx * 0.95f,
                // Bitiş noktası (tam circle tepe noktasının üstü)
                mCircleCenterXPx,
                mBarTopYPx - mBumpTotalRadiusPx
        );

        mBottomNavBackgroundPath.cubicTo(
                mCircleCenterXPx + mBumpTotalRadiusPx,
                mBarTopYPx - mBumpTotalRadiusPx * 0.95f,
                mBumpRightX - mBumpTotalRadiusPx * 0.95f,
                mBarTopYPx,
                mBumpRightX,
                mBarTopYPx
        );

        mBottomNavBackgroundPath.lineTo(mViewWidthPx, mBarTopYPx);
        mBottomNavBackgroundPath.lineTo(mViewWidthPx, mViewHeightPx);
        mBottomNavBackgroundPath.lineTo(0f, mViewHeightPx);
        mBottomNavBackgroundPath.close();

        mCanvas.drawPath(mBottomNavBackgroundPath, mBottomNavBackgroundPaint);
    }


    /**
     * Menü öğelerini (ikon ve başlıkları) Canvas üzerine çizer.
     * <p>
     * - Menüdeki her öğe için bölüm genişliği hesaplanır.
     * - Her öğenin ikon konumu ve boyutu belirlenir.
     * - Seçili öğe ikon ve metni, seçili renk ve animasyon değerleri ile çizilir.
     * - Seçilmeyen öğeler farklı pozisyonda ve renkte çizilir.
     * - Seçili öğenin metni animasyon değerlerine göre saydamlık ve offset ile hareket ettirilir.
     *
     * @param mCanvas Çizimin yapılacağı Canvas nesnesi.
     */
    private void DrawItems(Canvas mCanvas) {
        if (mBottomNavItemCount == 0) return;

        float mSectionWidthPx = mViewWidth / (float) mBottomNavItemCount;

        for (int mItemIndex = 0; mItemIndex < mBottomNavItemCount; mItemIndex++) {
            float mItemCenterXPx = mSectionWidthPx * mItemIndex + mSectionWidthPx / 2f;
            boolean mIsItemSelected = (mItemIndex == mBottomNavSelectedIndex);
            Drawable mItemIcon = mBottomNavItemIcons[mItemIndex];

            if (mItemIcon != null) {
                float mIconSizePx = mBottomNavItemIconSize[mItemIndex];
                if (mIsItemSelected) {
                    float mLeft = mItemCenterXPx - mIconSizePx / 2f;
                    float mTop  = mCircleCenterY - mIconSizePx / 2f;
                    mItemIcon.setBounds(
                            (int) mLeft,
                            (int) mTop,
                            (int)(mLeft + mIconSizePx),
                            (int)(mTop  + mIconSizePx)
                    );
                    mItemIcon.setTint(mBottomNavSelectedIconColor);
                    mItemIcon.draw(mCanvas);
                } else {
                    float mTop  = mBarTopY + ((mBarHeight - mIconSizePx) / 2f);
                    float mLeft = mItemCenterXPx - mIconSizePx / 2f;
                    mItemIcon.setBounds(
                            (int) mLeft,
                            (int) mTop,
                            (int)(mLeft + mIconSizePx),
                            (int)(mTop  + mIconSizePx)
                    );
                    mItemIcon.setTint(mBottomNavUnselectedIconColor);
                    mItemIcon.draw(mCanvas);
                }
            }

            if (mIsItemSelected) {
                String title = mBottomNavItemTitles[mItemIndex];
                if (title != null && !title.isEmpty()) {
                    float mTextTopBelowIcon = dpToPx(4f);
                    float mTextYPx = mBarTopY
                            + mBottomNavSelectedIconSize 
                            + mTextTopBelowIcon
                            - mBottomNavTextPaint.ascent();

                    mBottomNavTextPaint.setAlpha(255);
                    mCanvas.drawText(
                            title,
                            mItemCenterXPx,
                            mTextYPx,
                            mBottomNavTextPaint
                    );
                }
            }
        }
    }

    /**
     * Kullanıcının dokunma (touch) olaylarını yakalar ve işler.
     * <p>
     * - ACTION_DOWN (dokunma başlangıcı) olayında hangi menü öğesinin
     * seçildiği hesaplanır.
     * - Dokunulan öğe mevcut seçili öğeden farklıysa animasyonla seçim değiştirilir.
     * - Aynı öğeye dokunulursa, kayıtlı listener varsa çağrılır.
     * - Dokunma işlemi başarılıysa true döner.
     *
     * @param mMotionEvent Kullanıcı dokunma hareketini temsil eden MotionEvent nesnesi.
     * @return Dokunma olayının işlendiğine dair boolean değer.
     */
    @Override
    public boolean onTouchEvent(MotionEvent mMotionEvent) {
        if (mMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            if (mBottomNavItemCount == 0) {
                return super.onTouchEvent(mMotionEvent);
            }

            float mTouchX = mMotionEvent.getX();
            float mSectionWidthPx = mViewWidth / (float) mBottomNavItemCount;
            int mTappedIndex = (int) (mTouchX / mSectionWidthPx);

            if (mTappedIndex >= 0 && mTappedIndex < mBottomNavItemCount) {
                if (mTappedIndex != mBottomNavSelectedIndex) {
                    for (int mResetIndex = 0; mResetIndex < MAX_ITEMS_SIZE; mResetIndex++) {
                        mBottomNavItemTextAlphas[mResetIndex] = 0f;
                        mBottomNavItemTextOffsets[mResetIndex] = mBottomNavTextAnimDistancePX;
                    }
                    AnimationSelectionToIndex(mTappedIndex);
                } else {
                    if (mListener != null) {
                        mListener.onItemSelected(mBottomNavSelectedIndex);
                    }
                }
                performClick();
            }
            return true;
        }
        return super.onTouchEvent(mMotionEvent);
    }

    /**
     * Kullanıcının "click" (tıklama) eylemini tetikler.
     * <p>
     * Genellikle erişilebilirlik ve otomatik tıklama animasyonları için çağrılır.
     *
     * @return Her zaman true döner, tıklama olayı işlendi demektir.
     */
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    /**
     * Menüde seçili öğeyi animasyonla değiştirir.
     * <p>
     * - Önceki seçili öğe ve yeni seçili öğe belirlenir.
     * - Diğer öğeler varsayılan boyut ve saydamlığa ayarlanır.
     * - Seçili öğeler arasında ikon büyütme/küçültme, metin saydamlığı ve offset
     * animasyonları yapılır.
     * - Seçili daire animasyonu yeni öğeye hareket eder.
     * - Animasyon tamamlandığında, seçim durumu kesinleşir ve listener çağrılır.
     *
     * @param mNewSelectedIndex Yeni seçili menü öğesinin indeksi (0 tabanlı)
     */

    private void AnimationSelectionToIndex(final int mNewSelectedIndex) {
        if (mNewSelectedIndex < 0 || mNewSelectedIndex >= mBottomNavItemCount) {
            return;
        }

        if (mBottomNavItemCount == 0) return;

        final int mPreviousSelectedIndex = mBottomNavSelectedIndex;
        mBottomNavSelectedIndex = mNewSelectedIndex;

        for (int mItemIndex = 0; mItemIndex < mBottomNavItemCount; mItemIndex++) {
            if (mItemIndex != mPreviousSelectedIndex
                    && mItemIndex != mNewSelectedIndex) {
                mBottomNavItemIconSize[mItemIndex] = mBottomNavUnselectedIconSize;
                mBottomNavItemTextAlphas[mItemIndex] = 0f;
                mBottomNavItemTextOffsets[mItemIndex] = mBottomNavTextAnimDistancePX;
            }
        }

        mBottomNavItemIconSize[mPreviousSelectedIndex] = mBottomNavSelectedIconSize;
        mBottomNavItemTextAlphas[mPreviousSelectedIndex] = 1f;
        mBottomNavItemTextOffsets[mPreviousSelectedIndex] = 0f;

        final float mStartX = mAnimCenterX;
        final float mEndX = ComputeCenterXForIndex(mNewSelectedIndex);

        if (mCircleAnimator != null && mCircleAnimator.isRunning()) {
            mCircleAnimator.cancel();
        }

        ValueAnimator mCircleMoveAnimator = ValueAnimator.ofFloat(mStartX, mEndX);
        mCircleMoveAnimator.setDuration(mBottomNavItemAnimDuration);
        mCircleMoveAnimator.setInterpolator(
                MervInterpolator.get(mBottomNavAnimationInterpolatorType, getContext())
        );
        mCircleMoveAnimator.addUpdateListener(animation -> {
            mAnimCenterX = (float) animation.getAnimatedValue();
            invalidate();
        });
        mCircleAnimator = mCircleMoveAnimator;

        ValueAnimator mOldIconShrinkAnimator =
                ValueAnimator.ofFloat(mBottomNavSelectedIconSize, mBottomNavUnselectedIconSize);
        mOldIconShrinkAnimator.setDuration(mBottomNavItemAnimDuration);
        mOldIconShrinkAnimator.setInterpolator(
                MervInterpolator.get(mBottomNavAnimationInterpolatorType, getContext())
        );
        mOldIconShrinkAnimator.addUpdateListener(animation -> {
            mBottomNavItemIconSize[mPreviousSelectedIndex] =
                    (float) animation.getAnimatedValue();
            invalidate();
        });

        ValueAnimator mNewIconGrowAnimator =
                ValueAnimator.ofFloat(mBottomNavUnselectedIconSize, mBottomNavSelectedIconSize);
        mNewIconGrowAnimator.setDuration(mBottomNavItemAnimDuration);
        mNewIconGrowAnimator.setInterpolator(
                MervInterpolator.get(mBottomNavAnimationInterpolatorType, getContext())
        );
        mNewIconGrowAnimator.addUpdateListener(animation -> {
            mBottomNavItemIconSize[mNewSelectedIndex] =
                    (float) animation.getAnimatedValue();
            invalidate();
        });

        ValueAnimator mOldTextAlphaAnimator = ValueAnimator.ofFloat(1f, 0f);
        mOldTextAlphaAnimator.setDuration(mBottomNavItemAnimDuration);
        mOldTextAlphaAnimator.addUpdateListener(animation -> {
            mBottomNavItemTextAlphas[mPreviousSelectedIndex] =
                    (float) animation.getAnimatedValue();
            invalidate();
        });

        ValueAnimator mOldTextOffsetAnimator = ValueAnimator.ofFloat(0f, mBottomNavTextAnimDistancePX);
        mOldTextOffsetAnimator.setDuration(mBottomNavItemAnimDuration);
        mOldTextOffsetAnimator.addUpdateListener(animation -> {
            mBottomNavItemTextOffsets[mPreviousSelectedIndex] =
                    (float) animation.getAnimatedValue();
            invalidate();
        });

        ValueAnimator mNewTextAlphaAnimator = ValueAnimator.ofFloat(0f, 1f);
        mNewTextAlphaAnimator.setDuration(mBottomNavItemAnimDuration);
        mNewTextAlphaAnimator.addUpdateListener(animation -> {
            mBottomNavItemTextAlphas[mNewSelectedIndex] =
                    (float) animation.getAnimatedValue();
            invalidate();
        });

        ValueAnimator mNewTextOffsetAnimator = ValueAnimator.ofFloat(
                mBottomNavTextAnimDistancePX,
                0f
        );
        mNewTextOffsetAnimator.setDuration(mBottomNavItemAnimDuration);
        mNewTextOffsetAnimator.addUpdateListener(animation -> {
            mBottomNavItemTextOffsets[mNewSelectedIndex] =
                    (float) animation.getAnimatedValue();
            invalidate();
        });

        AnimatorSet mCombinedAnimatorSet = new AnimatorSet();
        mCombinedAnimatorSet.setInterpolator(
                MervInterpolator.get(mBottomNavAnimationInterpolatorType, getContext())
        );
        mCombinedAnimatorSet.playTogether(
                mCircleMoveAnimator,
                mOldIconShrinkAnimator,
                mNewIconGrowAnimator,
                mOldTextAlphaAnimator,
                mOldTextOffsetAnimator,
                mNewTextAlphaAnimator,
                mNewTextOffsetAnimator
        );
        mCombinedAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator mAnimation) {
                mBottomNavItemIconSize[mNewSelectedIndex] = mBottomNavSelectedIconSize;
                mBottomNavItemTextAlphas[mNewSelectedIndex] = 1f;
                mBottomNavItemTextOffsets[mNewSelectedIndex] = 0f;
                requestLayout();
                invalidate();
                if (mListener != null) {
                    mListener.onItemSelected(mNewSelectedIndex);
                }
            }
        });
        mCombinedAnimatorSet.start();
    }


    /**
     * Verilen menü öğesi indeksine karşılık gelen X koordinatını hesaplar.
     * <p>
     * Menü genişliği eşit parçalara bölünür ve indeksin ortası hesaplanır.
     * Eğer menü öğesi yoksa, görünümün tam ortasını döner.
     *
     * @param mIndex Menü öğesi indeksi (0 tabanlı)
     * @return İlgili öğenin merkez X koordinatı (piksel cinsinden)
     */
    private float ComputeCenterXForIndex(int mIndex) {
        if (mBottomNavItemCount <= 0) {
            return mViewWidth / 2f;
        }
        float mSectionWidth = ((float) mViewWidth) / mBottomNavItemCount;
        return mSectionWidth * mIndex + mSectionWidth / 2f;
    }

    /**
     * DP (density-independent pixels) değerini piksele dönüştürür.
     * <p>
     * Ekran yoğunluğuna göre uygun piksel değeri hesaplanır.
     *
     * @param mDP DP cinsinden değer
     * @return Piksel cinsinden karşılık gelen değer
     */
    private float dpToPx(float mDP) {
        if (mDP < 0) return 0f;

        final float MAX_DP = 1000f;
        float mClampedDP = Math.min(mDP, MAX_DP);

        return mClampedDP * getResources().getDisplayMetrics().density;
    }
    /**
     * Menüdeki tüm öğeleri temizler.
     * <p>
     * - Menü kaynak ID'sini sıfırlar.
     * - Menü öğelerinin ikon, başlık, boyut, saydamlık ve offset dizilerini sıfırlar.
     * - Menü öğe sayısını sıfırlar.
     * - Görünümü yeniden çizilmesi için invalidate() çağrılır.
     */
    public void ClearReloadItems() {
        mBottomNavMenuRes = 0;
        for (int mI = 0; mI < MAX_ITEMS_SIZE; mI++) {
            mBottomNavItemIcons[mI] = null;
            mBottomNavItemTitles[mI] = null;
            mBottomNavItemIconSize[mI] = mBottomNavUnselectedIconSize;
            mBottomNavItemTextAlphas[mI] = 0f;
            mBottomNavItemTextOffsets[mI] = mBottomNavTextAnimDistancePX;
        }
        mBottomNavItemCount = 0;
        invalidate();
    }
}
