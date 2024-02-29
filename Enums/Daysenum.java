package Enums;

public enum Daysenum
{
    MONDAY(20),
    TUESDAY(30),
    WEDNESDAY(50),
    THURSDAY(70),
    FRIDAY(80),
    SATURDAY(90),
    SUNDAY(100);

    final int fun;
    Daysenum(int FunLevel){
        this.fun = FunLevel;
    }

}
