package org.example.lab4.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExecutionTimerAspectTest {

    @Test
    void testMeasureExecutionTime_ShouldProceedAndReturnResult() throws Throwable {

        ExecutionTimerAspect aspect = new ExecutionTimerAspect();
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("OrderService.createOrder(..)");
        when(joinPoint.proceed()).thenReturn("SUCCESS_RESULT");


        Object result = aspect.measureExecutionTime(joinPoint);


        verify(joinPoint, times(1)).proceed();
        assertEquals("SUCCESS_RESULT", result);
    }
}