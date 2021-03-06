package com.zg.burgerjoint.mvp.presenters.impls

import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zg.burgerjoint.data.model.BurgerModel
import com.zg.burgerjoint.data.model.impls.BurgerModelImpl
import com.zg.burgerjoint.data.model.impls.MockBurgerModelImpl
import com.zg.burgerjoint.data.vos.BurgerVO
import com.zg.burgerjoint.dummy.getDummyBurgers
import com.zg.burgerjoint.mvp.views.MainView
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class MainPresenterImplTest {

    private lateinit var mPresenter: MainPresenterImpl

    @RelaxedMockK
    private lateinit var mView : MainView

    private lateinit var mBurgerModel : BurgerModel

    @Before
    fun setUpPresenter(){
        MockKAnnotations.init(this)
        BurgerModelImpl.init(ApplicationProvider.getApplicationContext())
        mBurgerModel = MockBurgerModelImpl
        mPresenter = MainPresenterImpl()
        mPresenter.initPresenter(mView)
        mPresenter.mBurgerModel = this.mBurgerModel
    }

    @Test
    fun onTapAddToCat_callAddBurgerToCart(){
        val tappedBurger = BurgerVO()
        tappedBurger.apply {
            burgerId = 1
            burgerName = "Big Mac"
            burgerImageUrl = ""
            burgerDescription = "Big Mac Burger"
            val imageView = ImageView(ApplicationProvider.getApplicationContext())
            mPresenter.onTapAddToCart(tappedBurger,imageView)
            verify {
                mView.animateAddBurgerToCart(tappedBurger,imageView)
            }
        }
    }

    @Test
    fun onTapCat_callNavigateToCartScreen(){
        mPresenter.onTapCart()
        verify {
            mView.navigateToCartScreen()
        }
    }

    @Test
    fun onTapBurger_callNavigateToBurgerDetails(){
        val tappedBurger = BurgerVO()
        tappedBurger.apply {
            burgerId = 1
            burgerName = "Big Mac"
            burgerImageUrl = ""
            burgerDescription = "Big Mac Burger"
            val imageView = ImageView(ApplicationProvider.getApplicationContext())
            mPresenter.onTapBurger(tappedBurger,imageView)
            verify {
                mView.navigateToBurgerDetailsScreenWithAnimation(tappedBurger.burgerId,imageView)
            }
        }
    }

    @Test
    fun onUIReady_callDisplayBurgerList_callDisplayCountInCart(){
        val lifeCyclerOwner: LifecycleOwner = mock(LifecycleOwner::class.java)
        val lifecycleRegistry = LifecycleRegistry(lifeCyclerOwner)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        `when`(lifeCyclerOwner.lifecycle).thenReturn(lifecycleRegistry)
        mPresenter.onUIReady(lifeCyclerOwner)
        verify {
            mView.displayBurgerList(getDummyBurgers())
        }
    }


}