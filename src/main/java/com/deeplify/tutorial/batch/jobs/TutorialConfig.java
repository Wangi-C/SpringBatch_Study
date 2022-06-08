package com.deeplify.tutorial.batch.jobs;

import com.deeplify.tutorial.batch.tasklets.TutorialTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TutorialConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep3(null))
                .next(simpleStep4(null))
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep3(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step_3");
                    log.info(">>>>> reqeustDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep4(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep4")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step_4");
                    log.info(">>>>> reqeustDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
