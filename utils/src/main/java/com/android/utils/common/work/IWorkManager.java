package com.android.utils.common.work;

/**
 * IWorkmanager 的接口类
 *
 * @author Simon
 */
public interface IWorkManager {
    /**
     * 开始可以处理work事件
     */
    public void start();

    /**
     * 结束处理work,并释放内存
     */
    public void stop();

    /**
     * 添加work到队列
     *
     * @param work 需要运行的work
     */
    public void addWork(IWork work);

    /**
     * 添加可延迟的work 到队列
     *
     * @param work  需要运行的work
     * @param delay 需要延迟的时间(ms)
     */
    public void addWork(IWork work, int delay);

    /**
     * 返回当前的运行状态
     *
     * @return true:正运行 false:没有运行
     */
    public boolean isRunning();

    /**
     * 返回当前是否停止
     *
     * @return true:停止 false：没有停止
     */
    public boolean isStop();
}
