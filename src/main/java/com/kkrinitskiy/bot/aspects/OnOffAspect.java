package com.kkrinitskiy.bot.aspects;

import com.kkrinitskiy.bot.services.OnOffService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OnOffAspect {
    @Autowired
    OnOffService onOffService;
}
