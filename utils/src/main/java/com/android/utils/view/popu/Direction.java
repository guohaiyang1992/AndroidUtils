package com.android.utils.view.popu;

/**
 * 方向类: 支持获取是否包含传入的方向 比如LEFT|BOTTOM
 */
public class Direction {
    //--新的方向--
    public static final int ABOVE = 1;//1  =》y
    public static final int BELOW = ABOVE << 1;//10 =》y
    public static final int TO_RIGHT = ABOVE << 2;//100 =》x
    public static final int TO_LEFT = ABOVE << 3;//1000 =》x
    public static final int ALIGN_RIGHT = ABOVE << 4;//10000 =》x
    public static final int ALIGN_LEFT = ABOVE << 5;//100000 =》x
    public static final int ALIGN_TOP = ABOVE << 6;//1000000 =》y
    public static final int ALIGH_BOTTOM = ABOVE << 7;//10000000 =》y
    public static final int CENTER_VER = ABOVE << 8;//100000000 =》y
    public static final int CENTER_HOR = ABOVE << 9;//1000000000 =》x


    /**
     * 真正的判断是否有值的函数
     *
     * @param value
     * @param direction
     * @return
     */
    public static boolean hasValue(int value, int direction) {
        //--数字转2进制--
        String valueBinary = Integer.toBinaryString(value);
        //--首先将值补全--
        String valueStr = appendZero(valueBinary);
        //--获取不同方向的index值--
        int index = 0;
        switch (direction) {
            case ABOVE:
                index = 9;
                break;
            case BELOW:
                index = 8;
                break;
            case TO_RIGHT:
                index = 7;
                break;
            case TO_LEFT:
                index = 6;
                break;
            case ALIGN_RIGHT:
                index = 5;
                break;
            case ALIGN_LEFT:
                index = 4;
                break;
            case ALIGN_TOP:
                index = 3;
                break;
            case ALIGH_BOTTOM:
                index = 2;
                break;
            case CENTER_VER:
                index = 1;
                break;
            case CENTER_HOR:
                index = 0;
                break;
        }
        //--获取对应位置的值是否为1，如果是返回true，反之返回false--
        char[] result = valueStr.toCharArray();
        return result.length == 10 && result[index] == '1';
    }


    /**
     * 数据不全时补充0
     *
     * @param value
     * @return
     */
    private static String appendZero(String value) {
        int count = value.length();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10 - count; i++) {
            stringBuffer.append("0");
        }
        stringBuffer.append(value);
        return stringBuffer.toString();
    }
}