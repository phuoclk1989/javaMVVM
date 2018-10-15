package com.redhelmet.alert2me.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * Annotation to be applied to a getter or setter function, that is stored in the binary output.
 * A [ViewModelKey] object will be the key in a Map generated by Dagger. The value will be the
 * ViewModel to be retrieved based on the key.
 * @see com.redhelmet.alert2me.ui.splash.SplashModule
 * for an usage example.
 */
@Target({ElementType.METHOD, ElementType.FIELD}
)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKey {
    Class value();
}
