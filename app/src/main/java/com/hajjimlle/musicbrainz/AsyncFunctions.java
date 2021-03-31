package com.hajjimlle.musicbrainz;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.supplyAsync;


public class AsyncFunctions {


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> CompletableFuture<Stream<T>> allOf(final Stream<CompletableFuture<T>> futures) {
        return allOf(toArray(futures));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static <T> CompletableFuture<Stream<T>> allOf(final CompletableFuture<T>[] promises) {
        return supplyAsync(() -> CompletableFuture.allOf(promises))
                .thenCompose(v -> completedFuture(stream(promises).map(CompletableFuture::join)));
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressWarnings("unchecked")
    private static <T> CompletableFuture<T>[] toArray(final Stream<CompletableFuture<T>> futures) {
        return (CompletableFuture<T>[]) futures.toArray(CompletableFuture[]::new);
    }
}
