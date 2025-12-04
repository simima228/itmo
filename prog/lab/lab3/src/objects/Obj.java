package objects;

import enums.IsAlive;

public abstract class Obj {

    private final String name;
    private String property = "";
    private IsAlive isAlive = IsAlive.INANIMATE;
    private boolean isChopped = false;

    public Obj(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getNameWithProperty() {
        return property + getName();
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public IsAlive getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(IsAlive isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isChopped() {
        return isChopped;
    }

    public void setChopped(boolean choped) {
        isChopped = choped;
    }

    @Override
    public String toString() {
        return getNameWithProperty();
    }

    @Override
    public int hashCode() {
        return getNameWithProperty().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Obj) {
            return name.equals(((Obj) obj).getNameWithProperty());
        }
        return false;
    }

}
