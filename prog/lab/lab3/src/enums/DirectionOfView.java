package enums;

public enum DirectionOfView {

    FORWARD(" посмотрел прямо"), BACK(" оглянулся"), LEFT(" взглянул влево"), RIGHT(" взглянул вправо");

    private final String s;

    DirectionOfView(String s) {
        this.s = s;
    }

    public String getText() {
        return s;
    }

}
