package com.pk.example.servicereceiver;

import android.content.Context;
import android.content.Intent;

import com.pk.example.BuildConfig;
import com.pk.example.servicereceiver.AlarmController;
import com.pk.example.servicereceiver.AlarmManagerBroadcastReceiver;
import com.pk.example.servicereceiver.SomeService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AlarmManagerBroadcastReceiverTest {
    Context context;
    AlarmManagerBroadcastReceiver sut;

    @Before
    public void setUp() throws Exception {
        context = RuntimeEnvironment.application.getApplicationContext();
        sut = new AlarmManagerBroadcastReceiver();
        sut.alarmController = mock(AlarmController.class);
    }

    @Test
    public void onReceivingAnyIntentShouldStartService() throws Exception {
        Intent expectedService = new Intent(context, SomeService.class);

        sut.onReceive(context, new Intent());

        assertEquals(expectedService.getComponent(),
                shadowOf(RuntimeEnvironment.application).getNextStartedService().getComponent());
    }

    @Test
    public void onReceivingAnyIntentShouldNotCollaborateWithAlarmController() throws Exception {
        sut.onReceive(context, new Intent());

        verify(sut.alarmController, never()).setAlarm(context);
    }

    @Test
    public void onBootCompleteShouldCollaborateWithAlarmController() throws Exception {
        sut.onReceive(context, new Intent("android.intent.action.BOOT_COMPLETED"));

        verify(sut.alarmController).setAlarm(context);
    }
}