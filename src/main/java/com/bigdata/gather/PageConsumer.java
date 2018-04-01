package com.bigdata.gather;

import com.bigdata.model.SpiderInfo;
import com.bigdata.model.Task;
import us.codecraft.webmagic.Page;

/**
 * PageConsumer
 */
@FunctionalInterface
public interface PageConsumer {
    void accept(Page page, SpiderInfo info, Task task);
}
