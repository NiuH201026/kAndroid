package com.github.tifezh.kchartlib.utils;

import android.os.Build;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class StrUtil {

    /**
     * 判断汉字
     *
     * @param str
     * @return
     */
    public static boolean isChinaText(String str) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断 时间
     *
     * @param str
     * @return
     */
    public static boolean isTimeText(String str) {
        return str.contains(":");
    }

    /**
     * 判断 正负数
     *
     * @param str
     * @return
     */
    public static boolean isPositiveOrNagativeNumberText(String str) {
        return str.substring(0, 1).matches("-");
    }

    /**
     * 保留2位小数
     */
    public static String floatToString(float value) {
        String s = String.format("%.2f", value);
        char end = s.charAt(s.length() - 1);
        while (s.contains(".") && (end == '0' || end == '.')) {
            s = s.substring(0, s.length() - 1);
            end = s.charAt(s.length() - 1);
        }
        return s;
    }

    //保留一位小数
    public static String getOneDecimals(double d) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足1位,会以0补足.
        String p = decimalFormat.format(d);//format 返回的是字符串
        return p;
    }


    //四舍五入保留正数
    public static String getPositiveNumber(double d) {
        NumberFormat nf = new DecimalFormat("#");
        return nf.format(d);
    }

    //进一法保留正数
    public static double getAndOnePositiveNumber(double d) {
        if (d > 0){
            if (d > Double.valueOf(getPositiveNumber(d))){
                return Double.valueOf(getPositiveNumber(d)) + 1;
            } else {
                return Double.valueOf(getPositiveNumber(d));
            }

        } else if (d < 0){
            if (d < Double.valueOf(getPositiveNumber(d))){
                return Double.valueOf(getPositiveNumber(d)) - 1;
            } else {
                return Double.valueOf(getPositiveNumber(d));
            }
        } else {
            return d;
        }
    }

    /**
     * 将double类型数据转换为百分比格式，并保留小数点前IntegerDigits位和小数点后FractionDigits位
     *
     * @param d
     * @param integerDigits  当为0或负数时不设置保留位数
     * @param fractionDigits 当为0或负数时不设置保留位数
     * @return
     */
    public static String getPercentFormat(double d, int integerDigits, int fractionDigits) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        if (integerDigits > 0) {
            nf.setMaximumIntegerDigits(integerDigits);//小数点前保留几位
        }
        if (fractionDigits > 0) {
            nf.setMinimumFractionDigits(fractionDigits);// 小数点后保留几位
        }
        String str = nf.format(d);
        if (d > 0) {
            return "+" + str;
        } else if (d == 0) {
            return 0 + "";
        } else {
            return str;
        }
    }

    //截取数字的前两位，小于两位直接返回
    public static int getNumberFrontTwoNumber(long l) {
        String str = String.valueOf(l);
        if (str.length() > 2) {
            return Integer.valueOf(str.substring(0, 2));
        } else {
            return Integer.valueOf(str);
        }
    }


    //获取长度
    public static int getNumberDigit(long l) {
        String str = String.valueOf(l);
        return str.length();
    }


    //前两位取5的倍数(CJL, MACD)
    public static long getFaveMultipleMinimum(long text) {
        if (text > 0) {
            long a = getNumberFrontTwoNumber(text);
            if (a < 10) {
                a = (a / 5 * 5 + 5);
            } else {
                if (a % 5 == 0) {
                    a = (long) ((a / 5 * 5) * Math.pow(10, getNumberDigit(text) - 2));
                } else {
                    a = (long) ((a / 5 * 5 + 5) * Math.pow(10, getNumberDigit(text) - 2));
                }
            }
            return a;
        } else if (text == 0) {
            return 0;
        } else {
            long aa = Math.abs(text);
            long a = getNumberFrontTwoNumber(aa);
            if (a < 10) {
                a = (a / 5 * 5 + 5);
            } else {
                if (a % 5 == 0) {
                    a = (long) ((a / 5 * 5) * Math.pow(10, getNumberDigit(aa) - 2));
                } else {
                    a = (long) ((a / 5 * 5 + 5) * Math.pow(10, getNumberDigit(aa) - 2));
                }
            }
            return -a;
        }
    }

    //取10的倍数(LME)
    public static long getZeroMultipleMinimum(long text) {
        if (text > 0) {
            text /= 10;
            text = text * 10 + 10;
            return text;
        } else if (text == 0) {
            return 0;
        } else {
            text /= 10;
            text = text * 10 - 10;
            return text;
        }
    }

    /**
     *  取n的倍数(LEM)
     * @param text 原数
     * @param n 倍数
     * @return n的被数
     */
    public static int getLemMultipleMinimum(long text, int n) {
        if (text > 0) {
            if (text % n == 0) {
                text = text / n * n;
            } else {
                text = text / n * n + n;
            }
            return (int) text;
        } else if (text == 0) {
            return 0;
        } else {
            int aa = (int) Math.abs(text);
            if (text % n == 0) {
                aa = aa / n * n;
            } else {
                aa = aa / n * n + n;
            }
            return -aa;
        }
    }

    //取100的倍数(LEM)
    public static long getMillionMultipleMinimum(long text) {
        if (text > 0) {
            text /= 100;
            text = text * 100 + 100;
            return text;
        } else if (text == 0) {
            return 0;
        } else {
            text /= 100;
            text = text * 100 - 100;
            return text;
        }
    }

    //LEM 时间格式化
    public static String getLEMDataStr(Long l) {
        String format = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat sdf = new SimpleDateFormat("YYMM");
            format = sdf.format(l);       //将Date类型转换成String类型
        } else {
            String year = String.valueOf(new Date(l).getYear() + 1900);
            year = year.substring(year.length() - 2, year.length());
            String month = String.valueOf(new Date(l).getMonth());
            if (month.length() == 1) {
                month = 0 + month;
            }
            format = year + month;
        }
        return format + "-3M";
    }

    /**
     *  LEM 右边轴的计算 aa[0] 最大值， aa[1] 最小值， aa[2] 等分个数, aa[3] 缩放量
     * @param height 传最大值
     * @param low 传最小值
     * @return 返回数组， aa[0] 最大值， aa[1] 最小值， aa[2] 等分个数, aa[3] 缩放量
     */
    public static int[] getLemRightValue(float height, float low) {
        int[] aa = new int[4];
        int count = 3;
        int c = 0;
        int scale = 0;
        int n = 3;
        int h = (int) Math.abs(getAndOnePositiveNumber(height));
        int l = (int) Math.abs(getAndOnePositiveNumber(low));
        if (height > 0 && low < 0) {
            if (Math.abs(height) > Math.abs(low)) {
                h = getLemMultipleMinimum(h, n);
                aa[0] = h;
                scale = h / n;
                aa[3] = scale;
                if (l % scale == 0) {
                    c = l / scale;
                } else {
                    c = l / scale + 1;
                }
                count += c;
                aa[2] = count;
                aa[1] = -scale * c;

            } else if (Math.abs(height) < Math.abs(low)) {
                l = getLemMultipleMinimum(l, n);
                aa[1] = -l;
                scale = l / n;
                aa[3] = scale;
                if (h % scale == 0) {
                    c = h / scale;
                } else {
                    c = h / scale + 1;
                }
                count += c;
                aa[2] = count;
                aa[0] = scale * c;

            } else if (Math.abs(height) == Math.abs(low)) {
                aa[0] = getLemMultipleMinimum(h, n);
                aa[1] = -getLemMultipleMinimum(l, n);
                aa[2] = count * 2;
                aa[3] = getLemMultipleMinimum(h, n) / n;
            }

        } else if (height > low && low >= 0) {
            aa[1] = 0;
            aa[0] = getLemMultipleMinimum(h, n);
            aa[2] = count;
            aa[3] = getLemMultipleMinimum(h, n) / n;

        } else if (height > low && height <= 0) {
            aa[0] = 0;
            aa[1] = -getLemMultipleMinimum(l, n);
            aa[2] = count;
            aa[3] = getLemMultipleMinimum(l, n) / n;
        }
        return aa;
    }


    public static void main(String[] argc) {
        System.out.println(StrUtil.getAndOnePositiveNumber(-3.1));

        System.out.println(StrUtil.getPositiveNumber(-0.1));

        System.out.println(StrUtil.getLemMultipleMinimum(33, 5));

        int[] aa = getLemRightValue(4.5f, -0.1f);

        System.out.println(aa[0] + ":" + aa[1] + ";" + aa[2] + ":" + aa[3]);
    }



}















