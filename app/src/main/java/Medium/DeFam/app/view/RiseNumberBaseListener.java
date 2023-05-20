package Medium.DeFam.app.view;


/**
 * 数字动画自定义
 *
 */
public interface RiseNumberBaseListener {
    public void start();

    public CountView withNumber(float number);

    public CountView withNumber(float number, boolean flag);

    public CountView withNumber(int number);

    public CountView setDuration(long duration);

    public void setOnEnd(CountView.EndListener callback);
}
