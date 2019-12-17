package com.sukumar.tetrasoft;

import com.sukumar.tetrasoft.base.ApiConfig;
import com.sukumar.tetrasoft.base.ApiInterface;
import com.sukumar.tetrasoft.module.mostPopular.dto.PopularDto;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Objects;

import static com.sukumar.tetrasoft.base.ApiBaseConfig.KEY;

@RunWith(JUnit4.class)
public class MostPopularTestCase {

    @Mock
    private ApiInterface apiInterface;

    @Mock
    private CompositeDisposable compositeDisposable;

    private PopularDto popular;

    @Before
    public void SETUP() throws Exception {
        MockitoAnnotations.initMocks(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Before
    @Test
    public void fetchMostPopular() {

        apiInterface = ApiConfig.Companion.getApiInstance();
        apiInterface.getPopularData(KEY).observeOn(Schedulers.trampoline()).subscribeOn(Schedulers.trampoline()).subscribe(new SingleObserver<PopularDto>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(PopularDto popularDto) {
                Assert.assertEquals(popularDto!=null, true);
                popular=popularDto;
            }

            @Override
            public void onError(Throwable e) {
                Assert.fail(e.getMessage());
            }
        });

    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_NULL_RETURN(){
        Assert.assertEquals(popular!=null, true);
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_STATUS_IS_NULL_RETURN(){
        Assert.assertEquals(popular.getStatus()!=null, true);
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_STATUS_IS_EMPTY_RETURN(){
        Assert.assertEquals(!Objects.requireNonNull(popular.getStatus()).isEmpty(), true);
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_STATUS_IS_OK_RETURN(){
        Assert.assertEquals(Objects.requireNonNull(popular.getStatus()).equalsIgnoreCase("OK"), true);
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_IS_NULL_RETURN() {
        if (popular != null) {
            Assert.assertEquals(popular.getResults() != null, true);
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_IS_GREATHER_THAN_0_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            Assert.assertEquals(popular.getResults().size()>0, true);
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_TITLE_IS_NULL_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertEquals(popular.getResults().get(i).getTitle()!=null, true);
            }
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_TITLE_IS_EMPTY_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertEquals(!popular.getResults().get(i).getTitle().isEmpty(), true);
            }
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_WRITTEN_BY_IS_NULL_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertEquals(popular.getResults().get(i).getByline()!=null, true);
            }
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_WRITTEN_BY_IS_EMPTY_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertEquals(!popular.getResults().get(i).getByline().isEmpty(), true);
            }
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_DATE_IS_NULL_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertEquals(popular.getResults().get(i).getPublishedDate()!=null, true);
            }
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_DATE_IS_EMPTY_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertEquals(!popular.getResults().get(i).getPublishedDate().isEmpty(), true);
            }
        }
    }



    @After
    @Test
    public void destroyDispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}
