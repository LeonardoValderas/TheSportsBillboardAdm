package com.valdroide.thesportsbillboardinstitution

import com.raizlabs.android.dbflow.config.FlowManager
import org.junit.Before
import org.mockito.MockitoAnnotations
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import java.io.IOException

 abstract class BaseTest{
    @Before
    @Throws(IOException::class)
     open fun setUp() {
        MockitoAnnotations.initMocks(this);
        if (RuntimeEnvironment.application != null) {
            val shadowApplication = Shadows.shadowOf(RuntimeEnvironment.application)
            shadowApplication.grantPermissions("android.permission.INTERNET")
        }
    }
}
