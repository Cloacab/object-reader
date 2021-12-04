package testObject;

import annotations.UserInput;
import model.CustomClass;
import rules.Rules;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@CustomClass
public class SpaceMarine implements Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @UserInput
    @Rules(value = "rules.NotNullRule")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @UserInput
    private Coordinates coordinates; //Поле не может быть null

    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @UserInput
    @Rules(value = "rules.HealthRule")
    private long health; //Значение поля должно быть больше 0
    @UserInput
    @Rules(value = "rules.NullableRule")
    private AstartesCategory category; //Поле может быть null
    @UserInput
    @Rules(value = "rules.NotNullRule")
    private Weapon weaponType; //Поле не может быть null
    @UserInput
    @Rules(value = "rules.NullableRule")
    private MeleeWeapon meleeWeapon; //Поле может быть null
    @UserInput
    private Chapter chapter; //Поле не может быть null

    static private final AtomicInteger counter = new AtomicInteger(-1);

    public SpaceMarine() {
        id = counter.incrementAndGet();
        name = "mock" + getId();
        setCoordinates(new Coordinates());
        setCreationDate(LocalDate.now());
        setHealth(100 + getId());
        setCategory(AstartesCategory.SCOUT);
        setWeaponType(Weapon.BOLT_RIFLE);
        setMeleeWeapon(MeleeWeapon.CHAIN_SWORD);
        setChapter(new Chapter());
    }

    public SpaceMarine(long id,
                       String name,
                       Coordinates coordinates,
                       LocalDate creationDate,
                       long health,
                       AstartesCategory category,
                       Weapon weaponType,
                       MeleeWeapon meleeWeapon,
                       Chapter chapter) {

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public SpaceMarine(String name,
                       Coordinates coordinates,
                       long health,
                       AstartesCategory category,
                       Weapon weaponType,
                       MeleeWeapon meleeWeapon,
                       Chapter chapter) {

        this.id = counter.incrementAndGet();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public static void setCounter(int value) {
        counter.set(value);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public String toString() {
        return "SpaceMarine{" +
                "\n\tid=" + id +
                "\n\tname='" + name + '\'' +
                "\n\tcoordinates=" + coordinates +
                "\n\tcreationDate=" + creationDate +
                "\n\thealth=" + health +
                "\n\tcategory=" + category +
                "\n\tweaponType=" + weaponType +
                "\n\tmeleeWeapon=" + meleeWeapon +
                "\n\tchapter=" + chapter +
                "\n}\n";
    }
}
