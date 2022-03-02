package sgf.view;

import sgf.controller.EnemyController;
import sgf.controller.loading.EnemyImageManager;
import sgf.model.Enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import  java.util.List;

/**
 * Panel for enemy's movement and appearance.
 */
public class EnemyViewImpl extends AbstractEnemyView {
    private static final long serialVersionUID = 6345414040020937047L;
    private static final int RGB_MAX = 255;     // Maximum value that a RGB parameter must assume.
    private EnemyController enemyController;
    private final EnemyImageManager imageController;      // Contains the links between enemy type and images.
    private final BufferedImage image;  // Empty image of total panel size to replace and hide previous effective enemy image.
    private List<Enemy> enemyList;       // List of enemies to be showed.
    private boolean isControllerSet;
    private final int tileSize;

    /**
     * Constructor that sets the image, image controller and list of enemies.
     * @param matrixSize Is the size that the enemy's image must have.
     * @param tileSize Is the size of a single tile in the map.
     */
    public EnemyViewImpl(final int matrixSize, final int tileSize) { // TODO: pass Level instead of first argument(?).
        this.tileSize = tileSize;
        this.image = new BufferedImage(matrixSize * tileSize, matrixSize * tileSize, BufferedImage.TYPE_INT_ARGB);
        this.imageController = new EnemyImageManager(tileSize);
        this.enemyList = new ArrayList<>();
    }

    @Override
    public void setList(final List<Enemy> enemies) {
        this.enemyList = enemies;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final var gImage = (Graphics2D) this.image.getGraphics();
        gImage.setBackground(new Color(RGB_MAX, RGB_MAX, RGB_MAX, 0));
        gImage.clearRect(0, 0, this.image.getWidth(), this.image.getHeight());  // Clear the image area before repaint in another position.

        // For each enemy in the list repaint it.
        for (final Enemy enemy : enemyList) {
            final var x = enemy.getPosition().getX();
            final var y = enemy.getPosition().getY();
            gImage.drawImage(this.imageController.spriteImage(enemy.getEnemyType()), (int) x, (int) y, this.tileSize, this.tileSize, null);
        }

        // The panel is covered with an empty image in order to hide the previous enemy image displayed.
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    @Override
    public void setController(final EnemyController controller) {
        if (!isControllerSet) {
            this.isControllerSet = true;
            this.enemyController = controller;
        }
    }
}