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
import retrofit2.Response;

import java.net.HttpURLConnection;
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
    public void SETUP() {
        MockitoAnnotations.initMocks(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Before
    @Test
    public void fetchMostPopular() {

        apiInterface = ApiConfig.Companion.getApiInstance();
        apiInterface.getPopularData(KEY).observeOn(Schedulers.trampoline()).subscribeOn(Schedulers.trampoline())
                .subscribe(new SingleObserver<Response<PopularDto>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<PopularDto> popularDtoResponse) {
                        Assert.assertNotNull(popularDtoResponse.body());
                        Assert.assertEquals(popularDtoResponse.code(), HttpURLConnection.HTTP_OK);
                        popular= popularDtoResponse.body();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Assert.fail(e.getMessage());
                    }
                });

    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_NULL_RETURN(){
        Assert.assertNotNull(popular);
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_STATUS_IS_NULL_RETURN(){
        Assert.assertNotNull(popular.getStatus());
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_STATUS_IS_EMPTY_RETURN(){
        Assert.assertTrue(!Objects.requireNonNull(popular.getStatus()).isEmpty());
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_STATUS_IS_OK_RETURN(){
        Assert.assertTrue(Objects.requireNonNull(popular.getStatus()).equalsIgnoreCase("OK"));
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_IS_NULL_RETURN() {
        if (popular != null) {
            Assert.assertNotNull(popular.getResults());
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_IS_GREATHER_THAN_0_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            Assert.assertTrue(popular.getResults().size() > 0);
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_TITLE_IS_NULL_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertNotNull(popular.getResults().get(i).getTitle());
            }
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_TITLE_IS_EMPTY_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertTrue(!Objects.requireNonNull(popular.getResults().get(i).getTitle()).isEmpty());
            }
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_WRITTEN_BY_IS_NULL_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertNotNull(popular.getResults().get(i).getByline());
            }
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_WRITTEN_BY_IS_EMPTY_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertTrue(!Objects.requireNonNull(popular.getResults().get(i).getByline()).isEmpty());
            }
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_DATE_IS_NULL_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertNotNull(popular.getResults().get(i).getPublishedDate());
            }
        }
    }

    @Test
    public void MOST_POPULAR_RESPONSE_IS_RESULTS_DATE_IS_EMPTY_RETURN() {
        if (popular != null && popular.getResults()!=null) {
            for (int i = 0; i < popular.getResults().size(); i++) {
                Assert.assertTrue(!Objects.requireNonNull(popular.getResults().get(i).getPublishedDate()).isEmpty());
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
