package kgz.dzhprojects.tanyshuubot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "user_data")
public class User {
    @Id
    private long id;
    private String name;
    private boolean likesSport;
    private boolean likesReading;
    private boolean likesActiveRest;
    private boolean likesAnimals;
    private boolean likesMoviesAndSeries;
    private boolean likesNewsAndPolitics;
    private boolean likesStudy;
    private boolean likesWalking;
    private boolean likesToEat;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "\nid=" + id +
                "\nname=" + name +
                "\nlikesSport=" + getAnswerOnRussian(likesSport) +
                "\nlikesReading=" + getAnswerOnRussian(likesReading) +
                "\nlikesActiveRest=" + getAnswerOnRussian(likesActiveRest) +
                "\nlikesAnimals=" + getAnswerOnRussian(likesAnimals) +
                "\nlikesMoviesAndSeries=" + getAnswerOnRussian(likesMoviesAndSeries) +
                "\nlikesNewsAndPolitics=" + getAnswerOnRussian(likesNewsAndPolitics) +
                "\nlikesStudy=" + getAnswerOnRussian(likesStudy) +
                "\nlikesWalking=" + getAnswerOnRussian(likesWalking) +
                "\nlikesToEat=" + getAnswerOnRussian(likesToEat) +
                "\nlikesToCook=" + getAnswerOnRussian(likesToCook);
    }

    private String getAnswerOnRussian(Boolean answrTrue) {
        if (answrTrue) {
            return "Да";
        } else {
            return "Нет";
        }

    }

    private boolean likesToCook = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && likesSport == user.likesSport && likesReading == user.likesReading && likesActiveRest == user.likesActiveRest && likesAnimals == user.likesAnimals && likesMoviesAndSeries == user.likesMoviesAndSeries && likesNewsAndPolitics == user.likesNewsAndPolitics && likesStudy == user.likesStudy && likesWalking == user.likesWalking && likesToEat == user.likesToEat && likesToCook == user.likesToCook && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, likesSport, likesReading, likesActiveRest, likesAnimals, likesMoviesAndSeries, likesNewsAndPolitics, likesStudy, likesWalking, likesToEat, likesToCook);
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

    public boolean isLikesSport() {
        return likesSport;
    }

    public void setLikesSport(boolean likesSport) {
        this.likesSport = likesSport;
    }

    public boolean isLikesReading() {
        return likesReading;
    }

    public void setLikesReading(boolean likesReading) {
        this.likesReading = likesReading;
    }

    public boolean isLikesActiveRest() {
        return likesActiveRest;
    }

    public void setLikesActiveRest(boolean likesActiveRest) {
        this.likesActiveRest = likesActiveRest;
    }

    public boolean isLikesAnimals() {
        return likesAnimals;
    }

    public void setLikesAnimals(boolean likesAnimals) {
        this.likesAnimals = likesAnimals;
    }

    public boolean isLikesMoviesAndSeries() {
        return likesMoviesAndSeries;
    }

    public void setLikesMoviesAndSeries(boolean likesMoviesAndSeries) {
        this.likesMoviesAndSeries = likesMoviesAndSeries;
    }

    public boolean isLikesNewsAndPolitics() {
        return likesNewsAndPolitics;
    }

    public void setLikesNewsAndPolitics(boolean likesNewsAndPolitics) {
        this.likesNewsAndPolitics = likesNewsAndPolitics;
    }

    public boolean isLikesStudy() {
        return likesStudy;
    }

    public void setLikesStudy(boolean likesStudy) {
        this.likesStudy = likesStudy;
    }

    public boolean isLikesWalking() {
        return likesWalking;
    }

    public void setLikesWalking(boolean likesWalking) {
        this.likesWalking = likesWalking;
    }

    public boolean isLikesToEat() {
        return likesToEat;
    }

    public void setLikesToEat(boolean likesToEat) {
        this.likesToEat = likesToEat;
    }

    public boolean isLikesToCook() {
        return likesToCook;
    }

    public void setLikesToCook(boolean likesToCook) {
        this.likesToCook = likesToCook;
    }

    public User() {
        likesSport = false;
        likesReading = false;
        likesActiveRest = false;
        likesAnimals = false;
        likesMoviesAndSeries = false;
        likesNewsAndPolitics = false;
        likesStudy = false;
        likesWalking = false;
        likesToEat = false;
    }

    public User(long id, String name, boolean likesSport, boolean likesReading, boolean likesActiveRest, boolean likesAnimals, boolean likesMoviesAndSeries, boolean likesNewsAndPolitics, boolean likesStudy, boolean likesWalking, boolean likesToEat, boolean likesToCook) {
        this.id = id;
        this.name = name;
        this.likesSport = likesSport;
        this.likesReading = likesReading;
        this.likesActiveRest = likesActiveRest;
        this.likesAnimals = likesAnimals;
        this.likesMoviesAndSeries = likesMoviesAndSeries;
        this.likesNewsAndPolitics = likesNewsAndPolitics;
        this.likesStudy = likesStudy;
        this.likesWalking = likesWalking;
        this.likesToEat = likesToEat;
        this.likesToCook = likesToCook;
    }
}
