package com.spring.batch.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class CustomChunkListener implements ChunkListener {

    Integer threadSleepTime;

    public CustomChunkListener(Integer threadSleepTime) {
        this.threadSleepTime = threadSleepTime;
    }

    @Override
    public void beforeChunk(
            ChunkContext chunkContext) {

    }

    @Override
    public void afterChunk(
            ChunkContext chunkContext) {

        System.out.println("*****************************************************");
        System.out.println("*            Records processed so far               *");
        System.out.println("*  Read Count : " + chunkContext.getStepContext()
                                                            .getStepExecution()
                                                            .getReadCount() + " Write Count : " + chunkContext
                .getStepContext().getStepExecution().getWriteCount());
        System.out.println("*****************************************************");

        try {
            Thread.sleep(threadSleepTime);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void afterChunkError(
            ChunkContext chunkContext) {

    }
}
