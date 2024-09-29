package kgz.dzhprojects.tanyshuubot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    private boolean likesMoviesSeries;
    private boolean likesNewsPolitics;
    private boolean likesStudy;
    private boolean likesWalking;
    private boolean likesToEat;
    private boolean likesToCook;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
        likesSport = false;
        likesReading = false;
        likesActiveRest = false;
        likesAnimals = false;
        likesMoviesSeries = false;
        likesNewsPolitics = false;
        likesStudy = false;
        likesWalking = false;
        likesToEat = false;
    }

    public User(long id, String name, boolean likesSport, boolean likesReading, boolean likesActiveRest, boolean likesAnimals, boolean likesMoviesSeries, boolean likesNewsPolitics, boolean likesStudy, boolean likesWalking, boolean likesToEat, boolean likesToCook) {
        this.id = id;
        this.name = name;
        this.likesSport = likesSport;
        this.likesReading = likesReading;
        this.likesActiveRest = likesActiveRest;
        this.likesAnimals = likesAnimals;
        this.likesMoviesSeries = likesMoviesSeries;
        this.likesNewsPolitics = likesNewsPolitics;
        this.likesStudy = likesStudy;
        this.likesWalking = likesWalking;
        this.likesToEat = likesToEat;
        this.likesToCook = likesToCook;
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

    public boolean isLikesMoviesSeries() {
        return likesMoviesSeries;
    }

    public void setLikesMoviesSeries(boolean likesMoviesAndSeries) {
        this.likesMoviesSeries = likesMoviesAndSeries;
    }

    public boolean isLikesNewsPolitics() {
        return likesNewsPolitics;
    }

    public void setLikesNewsPolitics(boolean likesNewsAndPolitics) {
        this.likesNewsPolitics = likesNewsAndPolitics;
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

    @Override
    public String toString() {
        return "\nидентификатор = " + id + "\n" +
                "телеграм = @" + name + "\n" +
                getAnswerOnRussian(likesSport) + "любит спорт" + "\n" +
                getAnswerOnRussian(likesReading) + "любит чтение" + "\n" +
                getAnswerOnRussian(likesActiveRest) + "любит активный отдых" + "\n" +
                getAnswerOnRussian(likesAnimals) + "любит животных" + "\n" +
                getAnswerOnRussian(likesMoviesSeries) + "любит просмотр сериалов и фильмов" + "\n" +
                getAnswerOnRussian(likesNewsPolitics) + "любит быть в курсе всех новостей и всей политики" + "\n" +
                getAnswerOnRussian(likesStudy) + "любит учиться и развиваться" + "\n" +
                getAnswerOnRussian(likesWalking) + "любит выходит на спокойные прогулки" + "\n" +
                getAnswerOnRussian(likesToEat) + "любит вкусно покушать" + "\n" +
                getAnswerOnRussian(likesToCook) + "любит готовить";
    }

    private String getAnswerOnRussian(Boolean answrTrue) {
        if (answrTrue) {
            return "";
        } else {
            return "не ";
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && likesSport == user.likesSport && likesReading == user.likesReading && likesActiveRest == user.likesActiveRest && likesAnimals == user.likesAnimals && likesMoviesSeries == user.likesMoviesSeries && likesNewsPolitics == user.likesNewsPolitics && likesStudy == user.likesStudy && likesWalking == user.likesWalking && likesToEat == user.likesToEat && likesToCook == user.likesToCook && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, likesSport, likesReading, likesActiveRest, likesAnimals, likesMoviesSeries, likesNewsPolitics, likesStudy, likesWalking, likesToEat, likesToCook);
    }

}
