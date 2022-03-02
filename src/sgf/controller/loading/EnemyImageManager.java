package sgf.controller.loading;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import sgf.model.EnemyType;

/**
 * This class loads enemy images.
 */
public class EnemyImageManager extends AbstractImageLoader<EnemyType> {
    private final Map<EnemyType, Image> enemySprite = new HashMap<>();
    private final int matrixSize;

    /**
     * Simple constructor that takes the enemies path names useful for the loading.
     * @param matrixSize Is useful in order to calculate the size of a single tile, which is also the enemy sprite's size.
     */
    public EnemyImageManager(final int matrixSize) {
        this.fillMap(super.getPathImage().getEnemyMap());
        this.matrixSize = matrixSize;
    }

    /**
     * Method that returns the image corresponding the given enemy type.
     * @param type Is the enemy type we want the image.
     * @return the correct image.
     */
    public Image spriteImage(final EnemyType type) {
        return this.enemySprite.get(type);
    }

    @Override
    public void fillMap(final Map<EnemyType, String> map) {
        for (final var elem : map.entrySet()) {
            this.enemySprite.put(elem.getKey(), this.loadRightImage(elem.getValue()));
        }
    }
}
