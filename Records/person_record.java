package Records;

public record person_record(String name, int age, String quote) {

    public person_record{
        if(age<20){
            throw new IllegalArgumentException("You are minor");
        }
    }

    public String ToUpper(){
        return name.toUpperCase();
    }
}
