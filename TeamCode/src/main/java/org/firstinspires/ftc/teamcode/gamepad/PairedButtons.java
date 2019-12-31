package org.firstinspires.ftc.teamcode.gamepad;

import java.util.function.Supplier;

public class PairedButtons<T> {
    private final Supplier<Boolean> cat;
    private final Supplier<Boolean> dog;
    private final Supplier<T> catValue;
    private final Supplier<T> dogValue;
    private final Supplier<T> defaultValue;

    public PairedButtons(Supplier<Boolean> cat, Supplier<T> catValue, Supplier<Boolean> dog, Supplier<T> dogValue, Supplier<T> defaultValue) {
        this.cat = cat;
        this.dog = dog;
        this.catValue = catValue;
        this.dogValue = dogValue;
        this.defaultValue = defaultValue;
    }

    public PairedButtons(Supplier<Boolean> cat, T catValue, Supplier<Boolean> dog, T dogValue, T defaultValue) {
        this(cat, () -> catValue, dog, () -> dogValue, () -> defaultValue);
    }

    public T getValue() {
        if (cat.get()) {
            return catValue.get();
        } else if (dog.get()) {
            return dogValue.get();
        } else {
            return defaultValue.get();
        }
    }

}
