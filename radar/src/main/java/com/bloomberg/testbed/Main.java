package com.bloomberg.testbed;

import java.util.Collection;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.bloomberg.core.BBException;
import com.bloomberg.core.BBLogger;
import com.bloomberg.core.VersionInfo;
import com.bloomberg.core.cmd.ConfigOption;
import com.bloomberg.core.cmd.SpringMain;
import com.google.common.collect.Lists;

public class Main extends SpringMain {
  private String taskInstanceName;
  private int maxWorkerThreads = 10; 
  
  BBLogger logger = BBLogger.getLogger(getClass());
  
  @Override
  public synchronized int main() throws BBException {    
    writeSystemProperties();
    return super.main();
  }
    
  private void writeSystemProperties(){
    System.setProperty("instance.name", taskInstanceName);
    System.setProperty("max.worker.threads", ""+maxWorkerThreads);
  }
  
  
  @Override
  protected Collection<? extends BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
    
    return Lists.newArrayList(new BeanFactoryPostProcessor() {
      @Override
      public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
      }
    });
  }
  
  /**
   * This method is reserved and we will add implementation in future
   * @return Returns {@link VersionInfo} object representing build information of the service
   */
  @Override
  public VersionInfo getVersion() {
    return null;
  }

  @ConfigOption(opt = "t", longOpt = "taskInstanceName", desc = "Task instance name of the application (regservice task name)", required = true)
  public String getTaskInstanceName() { return taskInstanceName; }
  public void setTaskInstanceName(String taskInstanceName) { 
    this.taskInstanceName = taskInstanceName;
  }

  @ConfigOption(opt = "wt", longOpt = "workerThreads", desc = "Max number of worker threads to execute", required = false)
  public int getMaxWorkerThreads() { return maxWorkerThreads; }
  public void setMaxWorkerThreads(int maxWorkerThreads) { 
    this.maxWorkerThreads = maxWorkerThreads;
  }
  
}
