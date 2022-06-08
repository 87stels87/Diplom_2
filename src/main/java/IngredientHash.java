import java.util.ArrayList;
import java.util.List;

public class IngredientHash {
    List<String> ingredients;

    public List<String> getIngredients() {
        return ingredients;
    }

    public IngredientHash(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "ingredients=" + ingredients +
                '}';
    }
}
