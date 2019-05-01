package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

/*
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.ttt0407projectnavigationapp", appContext.getPackageName());
    }
*/

    //DaoImpl daoImpl = DaoImpl.getInstance(getContext());

    String companyName;
    String companyTicker;
    String companyUrl;

    String companyName1 = "Pepsi Co";
    String companyTicker1 = "PEP";
    String companyUrl1 = "https://cdn4.iconfinder.com/data/icons/social-media-logos-6/512/49-pepsi-128.png";

    String companyName2 = "Google Inc";
    String companyTicker2 = "GOOGL";
    String companyUrl2 = "https://www.trainingtoyou.com/wp-content/uploads/2018/08/2000px-Google__G__Logo.svg_.png";

    String productName;
    String productUrl;
    String productImageUrl;

    String productName1 = "Pepsi Socks";
    String productUrl1 = "https://www.etsy.com/listing/486322861/pepsi-socks";
    String productImageUrl1 = "https://i.etsystatic.com/13514566/r/il/49e306/1432356691/il_794xN.1432356691_fbmk.jpg";

    String productName2 = "Kanye Mug";
    String productUrl2 = "https://www.etsy.com/listing/650234767/kanye-twitter-youre-not-kanye-west-mug";
    String productImageUrl2 = "https://i.etsystatic.com/11558999/r/il/04ffec/1694778555/il_794xN.1694778555_flpm.jpg";


    //invokes the MainActity for testing
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateAddObjects() throws InterruptedException {

        Integer wait = 0;

        // start WatchListFragment

        ////////
        // START Watch List
/*
        // click button to add company
        if(daoImpl.getLisCompanies().size() > 0) {
            onView(withId(R.id.img_rhs)).perform(click());
        }
        else {
            onView(withId(R.id.txt_empty)).perform(click());
        }
*/
        onView(withId(R.id.img_rhs)).perform(click());


        ////////
        // -> Add Company
        companyName = companyName1 + " (top save)";
        companyTicker = companyTicker1 ;
        companyUrl = companyUrl1;

        // fill in form
        onView(withId(R.id.edt_company_name)).perform(replaceText(companyName));
        onView(withId(R.id.edt_company_stock_ticker)).perform(replaceText(companyTicker));
        onView(withId(R.id.edt_company_image_url)).perform(replaceText(companyUrl));

        // click save on top RHS
        onView(withIndex(withId(R.id.txt_rhs), 1)).perform(click());

        ////////
        // -> Watch List
        Thread.sleep(wait);

        // add another company, saving w on-screen button
        onView(withId(R.id.img_rhs)).perform(click());

        ////////
        // -> Add Company
        companyName = companyName1 + " (button save)";
        companyTicker = companyTicker1 ;
        companyUrl = companyUrl1;

        // fill in form
        onView(withId(R.id.edt_company_name)).perform(typeText(companyName), closeSoftKeyboard());
        onView(withId(R.id.edt_company_stock_ticker)).perform(typeText(companyTicker), closeSoftKeyboard());
        onView(withId(R.id.edt_company_image_url)).perform(replaceText(companyUrl));

        Thread.sleep(wait);

        // click save button under text fields
        onView(withId(R.id.btn_add_company)).perform(click());

        ////////
        // -> Watch List
        Thread.sleep(wait);
        onView(allOf(withId(R.id.txt_company_info), withText(companyName + " (" + companyTicker + ")" ))).perform(click());

        ////////
        // -> Company
        Thread.sleep(wait);
        onView(withIndex(withId(R.id.img_rhs), 1)).perform(click());

        ////////
        // -> Add Product
        productName = productName1;
        productUrl = productUrl1;
        productImageUrl = productImageUrl1;

        // fill in form
        onView(withId(R.id.edt_product_name)).perform(typeText(productName), closeSoftKeyboard());
        onView(withId(R.id.edt_product_url)).perform(typeText(productUrl), closeSoftKeyboard());
        onView(withId(R.id.edt_product_image_url)).perform(replaceText(productImageUrl));

        // click save button under text fields
        onView(withId(R.id.btn_add_product)).perform(click());

        ////////
        // -> Company
        Thread.sleep(wait * 10);
    }


    @Test
    public void validateEditObjects() throws InterruptedException {

        DaoImpl daoImpl = DaoImpl.getInstance(getContext());
        List<Company> lisCompanies = daoImpl.getLisCompanies();
        List<Product> lisProducts;


        Integer wait = 100;

        ////////
        // start WatchList Fragment
        Thread.sleep(1000);

        // longClick to pop-up menu, click edit
        onData(allOf(is(instanceOf(Company.class)), is(lisCompanies.get(3)))).perform(longClick());
        Thread.sleep(wait); // let it load
        onView(withText("Edit")).perform(click());

        ////////
        // -> EditCompanyFragment
        companyName = companyName1 + " (WLF edit; top RHS save)";
        companyTicker = companyTicker1 ;
        companyUrl = companyUrl1;

        // fill in form
        onView(withId(R.id.edt_company_name)).perform(replaceText(companyName));
        onView(withId(R.id.edt_company_stock_ticker)).perform(replaceText(companyTicker));
        onView(withId(R.id.edt_company_image_url)).perform(replaceText(companyUrl));

        // click save on top RHS
        onView(withIndex(withId(R.id.txt_rhs), 1)).perform(click());

        ////////
        // -> WatchListFragment
        Thread.sleep(wait);

        // longClick to pop-up menu, click edit
        onData(allOf(is(instanceOf(Company.class)), is(lisCompanies.get(3)))).perform(longClick());
        Thread.sleep(wait);
        onView(withText("Edit")).perform(click());

        // -> EditCompanyFragment
        companyName = companyName2 + " (WLF edit; button save)";
        companyTicker = companyTicker2 ;
        companyUrl = companyUrl2;

        // fill in form
        onView(withId(R.id.edt_company_name)).perform(replaceText(companyName));
        onView(withId(R.id.edt_company_stock_ticker)).perform(replaceText(companyTicker));
        onView(withId(R.id.edt_company_image_url)).perform(replaceText(companyUrl));

        // click save button under text fields
        onView(withId(R.id.btn_edit_company)).perform(click());

        ////////
        // -> WatchListFragment
        Thread.sleep(wait);

        // open company page
        onData(allOf(is(instanceOf(Company.class)), is(lisCompanies.get(1)))).perform(click());

        ////////
        // -> CompanyFragment
        Thread.sleep(wait);
        onView(withId(R.id.img_company_fragment_logo)).perform(click());
        //onView(withIndex(withId(R.id.img_company_logo), lisCompanies.size())).perform(click());

        ////////
        // -> EditCompanyFragment (from CompanyFragment)
        Thread.sleep(wait);

        companyName = companyName1 + " (CF edit; button save)";
        companyTicker = companyTicker1 ;
        companyUrl = companyUrl1;

        // fill in form
        onView(withId(R.id.edt_company_name)).perform(replaceText(companyName));
        onView(withId(R.id.edt_company_stock_ticker)).perform(replaceText(companyTicker));
        onView(withId(R.id.edt_company_image_url)).perform(replaceText(companyUrl));

        // click save button under text fields
        onView(withId(R.id.btn_edit_company)).perform(click());

        ////////
        // -> Company
        Thread.sleep(wait);
        // pull from DAO
        lisProducts = daoImpl.getLisProducts();
        onView(allOf(withId(R.id.txt_product_description), withText(lisProducts.get(0).getStrName().toString()))).perform(longClick());
        Thread.sleep(wait);
        onView(withText("Edit")).perform(click());

        ////////
        // -> Edit Product
        Thread.sleep(wait);
        productName = productName2;
        productUrl = productUrl2;
        productImageUrl = productImageUrl2;

        // fill in form
        onView(withId(R.id.edt_product_name)).perform(replaceText(productName));
        onView(withId(R.id.edt_product_url)).perform(replaceText(productUrl));
        onView(withId(R.id.edt_product_image_url)).perform(replaceText(productImageUrl));

        // click save button under text fields
        onView(withId(R.id.btn_edit_product)).perform(click());

        ////////
        // -> Company
        Thread.sleep(10 * wait);
    }

    @Test
    public void validateAppNavigation() throws InterruptedException {

        DaoImpl daoImpl = DaoImpl.getInstance(getContext());
        List<Company> lisCompanies = daoImpl.getLisCompanies();
        List<Product> lisProducts;
        Company company;

        Integer toolbarIndex = 0;
        Integer wait = 100;

        ////////
        // START WatchList
        Thread.sleep(2000);
        onView(withId(R.id.img_rhs)).perform(click());

        ////////
        toolbarIndex++;
        // -> AddCompany
        Thread.sleep(wait);
        onView(withIndex(withId(R.id.txt_lhs), toolbarIndex)).perform(click());

        ////////
        toolbarIndex--;
        // -> WatchList
        Thread.sleep(wait);
        onData(allOf(is(instanceOf(Company.class)), is(lisCompanies.get(0)))).perform(longClick());
        Thread.sleep(wait);
        onView(withText("Edit")).perform(click());

        ////////
        toolbarIndex++;
        // -> EditCompany
        Thread.sleep(wait);
        onView(withIndex(withId(R.id.txt_lhs), toolbarIndex)).perform(click());

        ////////
        toolbarIndex--;
        // -> WatchList
        Thread.sleep(wait);
        onData(allOf(is(instanceOf(Company.class)), is(lisCompanies.get(0)))).perform(click());

        ////////
        toolbarIndex++;
        // -> Company
        Thread.sleep(wait);
        // pull data from DAO
        company = daoImpl.getSelectedCompany();
        lisProducts = daoImpl.getLisProducts();
        // navigate
        onView(withIndex(withId(R.id.img_rhs), toolbarIndex)).perform(click());

        ////////
        toolbarIndex++;
        // -> AddProduct
        Thread.sleep(wait);
        onView(withIndex(withId(R.id.img_lhs), toolbarIndex)).perform(click());

        ////////
        toolbarIndex--;
        // -> Company
        Thread.sleep(wait);
        onView(withId(R.id.img_company_fragment_logo)).perform(click());

        ////////
        toolbarIndex++;
        // -> EditCompany
        Thread.sleep(wait);
        onView(withIndex(withId(R.id.txt_lhs), toolbarIndex)).perform(click());

        ////////
        toolbarIndex--;
        // -> Company
        Thread.sleep(wait);
        //onData(allOf(is(instanceOf(Product.class)), is(lisProducts.get(1)))).perform(click());
        //onData(allOf(is(instanceOf(Company.class)), is(lisCompanies.get(0)))).perform(longClick());
        //onView(withId(R.id.lsv_products)).perform(click());
        //onData(withItemContent()).withId
        onView(allOf(withId(R.id.txt_product_description), withText(lisProducts.get(0).getStrName().toString()))).perform(click());


        ////////
        toolbarIndex++;
        // -> Product
        Thread.sleep(wait);
        onView(withIndex(withId(R.id.txt_rhs), toolbarIndex)).perform(click());

        ////////
        toolbarIndex++;
        // -> EditProduct
        Thread.sleep(wait);
        onView(withIndex(withId(R.id.txt_lhs), toolbarIndex)).perform(click());

        ////////
        toolbarIndex--;
        // -> Product
        Thread.sleep(wait);
        onView(withIndex(withId(R.id.img_lhs), toolbarIndex)).perform(click());

        ////////
        toolbarIndex--;
        // -> Company
        Thread.sleep(wait);
        onView(withIndex(withId(R.id.img_lhs), toolbarIndex)).perform(click());

        ////////
        toolbarIndex--;
        // -> WatchList
        Thread.sleep(wait * 10);
    }


    ////////
    ////////
    ////////

    // allows clicking view with repeat name based on index (start at 0)
    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

}
