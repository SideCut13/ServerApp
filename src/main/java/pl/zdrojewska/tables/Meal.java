package pl.zdrojewska.tables;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String summary;
    private String description;
    private String date;
    private String imagePath;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id")
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meal meal = (Meal) o;

        if (!id.equals(meal.id)) return false;
        if (title != null ? !title.equals(meal.title) : meal.title != null) return false;
        if (summary != null ? !summary.equals(meal.summary) : meal.summary != null) return false;
        if (description != null ? !description.equals(meal.description) : meal.description != null) return false;
        if (date != null ? !date.equals(meal.date) : meal.date != null) return false;
        if (imagePath != null ? !imagePath.equals(meal.imagePath) : meal.imagePath != null) return false;
        return client != null ? client.equals(meal.client) : meal.client == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }
}
