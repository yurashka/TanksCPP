package tanks;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    Random randomValue = new Random();
    Pane playfieldLayer;
    Pane scoreLayer;
    Pane debugLayer;
    Image player1Image;
    Image player2Image;
    Image enemy1Image;
    Image enemy2Image;
    Image enemy3Image;
    Image bulletImage;
    ImageView gamebackgroung;
    Image playerMissileImage;
    List<Player> players = new ArrayList<>();
    List<EnemyTank> enemies = new ArrayList<>();
    List<Bullet> player1bullets = new ArrayList<>();
    List<Bullet> player2bullets = new ArrayList<>();
    List<Bullet> bullets = new ArrayList<>();
    List<Missile> playerMissileList = new ArrayList<>();
    AudioClip audio;
    AudioClip audio1;
    AudioClip audio2;
    AudioClip audio3;
    AudioClip audio4;
    Text Player1Score = new Text();
    Text Player2Score = new Text();
    Text Level = new Text();
    Label TotalEnemies = new Label();
    boolean collision = false;
    Scene scene;
    Scene mainMenu;
    Scene difficultyAndLoad;
    AnimationTimer gameLoop;

    /**
     * start game
     */
    private void startGame() {
        gameLoop.start();
    }

    /**
     * stop game
     */
    private void pauseGame() {
        Settings.gamePaused = true;
        gameLoop.stop();
    }

    /**
     * resume game
     */
    private void resumeGame() {
        Settings.gamePaused = false;
        gameLoop.start();
    }

    @Override
    /**
     * start timer for game
     */
    public void start(Stage primaryStage) throws Exception {
        primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, globalKeyEventHandler);
        StackPane menu = new StackPane();
        mainMenu = new Scene(menu, 300, 300);
        StackPane difficulty = new StackPane();
        difficultyAndLoad = new Scene(difficulty, 300, 300);
        Button btnDifEasy = new Button("Easy players:1");
        Button btnDifNormal = new Button("Normal players:2");
        Button btnDifHard = new Button("Hard players:1");
        Button btnBack = new Button("back");
        Button btnAuto = new Button("Auto");
        difficulty.getChildren().addAll(btnDifEasy, btnDifNormal, btnDifHard, btnBack, btnAuto);
        btnDifEasy.setOnAction(e -> {
            stopGame();
            primaryStage.setScene(scene);
            Settings.player2Lives = 0;
            loadGame();
            startGame();
        });
        btnDifNormal.setOnAction(e -> {
            stopGame();
            Settings.playerShipSpeed = 3.0;
            Settings.playerShipHealth = 300;
            Settings.enemy = 36;
            primaryStage.setScene(scene);
            loadGame();
            startGame();
        });
        btnDifHard.setOnAction(e -> {
            stopGame();
            Settings.playerShipSpeed = 3.0;
            Settings.playerShipHealth = 10;
            Settings.enemy = 48;
            primaryStage.setScene(scene);
            Settings.player2Lives = 0;
            loadGame();
            startGame();
        });
        btnBack.setOnAction(e -> {
                    primaryStage.setScene(mainMenu);
                    for (Player player : players)
                        player.updateUI();
                    for (EnemyTank enemy : enemies)
                        enemy.updateUI();
                    for (Bullet bullet : bullets)
                        bullet.updateUI();
                    players.remove(true);
                    enemies.remove(true);
                    bullets.remove(true);
                    for (Player player : players)
                        player.updateUI();
                    for (EnemyTank enemy : enemies)
                        enemy.updateUI();
                    for (Bullet bullet : bullets)
                        bullet.updateUI();
                    stopGame();
                }
        );
        btnAuto.setOnAction(e -> {
            Settings.automove = true;
            stopGame();
            Settings.playerShipSpeed = 3.0;
            Settings.playerShipHealth = 300;
            Settings.enemy = 36;
            primaryStage.setScene(scene);
            loadGame();
            startGame();
        });
        btnDifEasy.setTranslateY(-40);
        btnDifNormal.setTranslateY(0);
        btnDifHard.setTranslateY(40);
        btnBack.setTranslateY(80);
        btnAuto.setTranslateY(120);
        Button btnNewGame = new Button("NEW GAME");
        Button btnExit = new Button("EXIT");
        Button btnResume = new Button("RESUME");
        Button btnMenu = new Button("Menu");
        primaryStage.setResizable(false);
        btnExit.setTranslateY(btnExit.getMaxHeight() + 40);
        btnNewGame.setOnAction(e -> {
            primaryStage.setScene(difficultyAndLoad);
            clearGame();
        });
        btnExit.setOnAction(e -> System.exit(0));
        btnResume.setOnAction(e -> {
            resumeGame();
            primaryStage.setScene(scene);
            debugLayer.setVisible(false);
        });
        btnMenu.setOnAction(e -> {
            stopGame();
            debugLayer.setVisible(false);
            gameLoop.stop();
            primaryStage.setScene(mainMenu);
            enemies.clear();
            bullets.clear();
            player1bullets.clear();
            player2bullets.clear();
        });
        menu.getChildren().addAll(btnNewGame, btnExit, btnResume, btnMenu);
        primaryStage.setScene(mainMenu);
        primaryStage.show();
        Group root = new Group();
        playfieldLayer = new Pane();
        scoreLayer = new Pane();
        debugLayer = new Pane();
        debugLayer.getChildren().addAll(btnResume, btnMenu);
        debugLayer.setVisible(false);
        debugLayer.setStyle("-fx-background-color:rgba(0,0,0,0.3)");
        root.getChildren().add(playfieldLayer);
        root.getChildren().add(scoreLayer);
        root.getChildren().add(debugLayer);
        scene = new Scene(root, Settings.SCENE_WIDTH + 300, Settings.SCENE_HEIGHT);
        debugLayer.setTranslateY(scene.getHeight() / 5);
        debugLayer.setTranslateX(scene.getWidth() / 4);
        btnResume.setTranslateX(btnResume.getMaxWidth() - 65);
        btnResume.setTranslateY(btnResume.getMaxHeight() - 70);
        btnMenu.setTranslateX(btnMenu.getMaxWidth() - 65);
        btnMenu.setTranslateY(btnMenu.getMaxWidth() - 30);
        primaryStage.setTitle("Tanks");
        primaryStage.show();
        loadGame();
        createScoreLayer();
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                createPlayers();
                // player input
                for (Player player : players) {
                    if (Settings.automove) {
                        {
                            if (player.getPlayerNumber() == 2) {
                                player.processInput();
                                player.move();
                                continue;
                            }
                            player.findTarget(enemies);
                            player.autoMove();
                            if (!noEnemiesOnLine(player, enemies))
                                spawnBullet(player, player1bullets);
                            if (noEnemiesOnLine(player, enemies))
                                spawnSecondaryWeaponObjects(player);
                        }
                    } else
                        player.processInput();
                    player.move();
                }
                for (Player player : players) {
                    player.chargePrimaryWeapon();
                }
                for (Player player : players) {
                    if (Settings.player1IsAlive && player.isFirePrimaryWeapon() && player.getPlayerNumber() == 1) {
                        audio.play();
                        spawnBullet(player, player1bullets);
                        player.unchargePrimaryWeapon();
                    }
                    if (Settings.player2IsAlive && player.isFirePrimaryWeapon() && player.getPlayerNumber() == 2) {
                        audio.play();
                        spawnBullet(player, player2bullets);
                        player.unchargePrimaryWeapon();
                    }
                }
                //for missile found targets
                for (Missile missile : playerMissileList) {
                    missile.findTarget(enemies);
                }
                for (Player player : players) {
                    spawnSecondaryWeaponObjects(player);
                }
                playerMissileList.forEach(sprite -> sprite.move());
                playerMissileList.forEach(sprite -> sprite.updateUI());
                playerMissileList.forEach(sprite -> sprite.checkRemovability());
                removeSprites(playerMissileList);
                // add random enemies
                if (Settings.enemyTotal < Settings.enemy && enemies.size() < 3) {
                    spawnEnemies(true);
                    Settings.enemyCount++;
                    spawnEnemies(false);
                }
                if (!enemies.isEmpty()) {
                    if (randomValue.nextBoolean()) {
                        for (EnemyTank enemy : enemies) {
                            if (bullets.size() < 1) {
                                if (noEnemiesOnLine(enemy, enemies))
                                    for (Player player : players) {
                                        if (!randomValue.nextBoolean() && !playersOnLine(enemy, player)) {
                                            spawnBullet(enemy, bullets);
                                        }
                                    }
                            }
                        }
                    }
                }
                // movement
                for (EnemyTank enemy : enemies) {
                    enemy.chargeChangeMovement();
                }
                enemies.forEach(sprite -> sprite.move());
                bullets.forEach(sprite -> sprite.move());
                player1bullets.forEach(sprite -> sprite.move());
                player2bullets.forEach(sprite -> sprite.move());
                // check collisions
                checkCollisions();
                // update sprites in scene
                for (Player player : players) {
                    player.updateUI();
                }
                enemies.forEach(sprite -> sprite.updateUI());
                bullets.forEach(sprite -> sprite.updateUI());
                player1bullets.forEach(sprite -> sprite.updateUI());
                player2bullets.forEach(sprite -> sprite.updateUI());
                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());
                bullets.forEach(sprite -> sprite.checkRemovability());
                for (Player player : players) {
                    player.checkRemovability();
                }
                player1bullets.forEach(sprite -> sprite.checkRemovability());
                player2bullets.forEach(sprite -> sprite.checkRemovability());
                // remove removables from list, layer, etc
                removeSprites(enemies);
                removeSprites(bullets);
                removeSprites(player1bullets);
                removeSprites(player2bullets);
                removeSprites(players);
                // update score, health, etc
                updateScore();
            }
        };
    }

    /**
     * clear game and restore basic settings
     */
    private void clearGame() {
        players.remove(true);
        enemies.remove(true);
        bullets.remove(true);
        for (Player player : players)
            player.updateUI();
        for (EnemyTank enemy : enemies)
            enemy.updateUI();
        for (Bullet bullet : bullets)
            bullet.updateUI();
        removeSprites(enemies);
        removeSprites(bullets);
        removeSprites(player1bullets);
        removeSprites(player2bullets);
        removeSprites(players);
        playfieldLayer.getChildren().clear();
        players.clear();
        enemies.clear();
        bullets.clear();
        player1bullets.clear();
        player2bullets.clear();
        playerMissileList.clear();
        Settings.playerShipSpeed = 4.0;
        Settings.playerShipHealth = 1000.0;
        Settings.playerMissileSpeed = 15.0;
        Settings.playerMissileHealth = 50.0;
        Settings.player1Lives = 3;
        Settings.player1IsAlive = false;
        Settings.player2Lives = 3;
        Settings.player2IsAlive = false;
        Settings.gamePaused = false;
        Settings.player1Score = 0;
        Settings.player2Score = 0;
        Settings.automove = false;
        Settings.enemy = 24;
        Settings.enemyCount = 0;
        Settings.enemyTotal = 0;
        Settings.BulletsAct = 1;
        Settings.FIRE = true;
        Settings.fontsSize = 24;
    }

    private EventHandler<KeyEvent> globalKeyEventHandler = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {

            // toggle pause
            if (event.getCode() == KeyCode.P) {
                debugLayer.setVisible(!debugLayer.isVisible());
                if (Settings.gamePaused) {
                    resumeGame();
                } else {
                    pauseGame();
                }
            }
            // toggle debug overlay
            else if (event.getCode() == KeyCode.F10) {
                debugLayer.setVisible(!debugLayer.isVisible());
            }
            // take screenshot, open save dialog and save it
            else if (event.getCode() == KeyCode.ESCAPE) {
            }
        }
    };

    /**
     * spawn secondary weapon objects
     */
    private void spawnSecondaryWeaponObjects(Player player) {
        player.chargeSecondaryWeapon();
        if (player.isFireSecondaryWeapon()) {
            Image image = playerMissileImage;
            double x = player.getSecondaryWeaponX() - image.getWidth() / 2.0;
            double y = player.getSecondaryWeaponY();
            Missile missile = new Missile(playfieldLayer, image, x, y);
            playerMissileList.add(missile);
            player.unchargeSecondaryWeapon();
        }
    }

    /**
     * load game images,sounds,
     */
    private void loadGame() {
        player1Image = new Image(getClass().getResource("player1.png").toExternalForm());
        player2Image = new Image(getClass().getResource("player2.png").toExternalForm());
        enemy1Image = new Image(getClass().getResource("battle1.png").toExternalForm());
        enemy2Image = new Image(getClass().getResource("battle2.png").toExternalForm());
        enemy3Image = new Image(getClass().getResource("battle2.png").toExternalForm());
        bulletImage = new Image(getClass().getResource("bullet1.png").toExternalForm());
        gamebackgroung = new ImageView(getClass().getResource("backgroung.png").toExternalForm());
        playfieldLayer.getChildren().add(gamebackgroung);
        audio = new AudioClip(getClass().getResource("fire.wav").toString());
        audio1 = new AudioClip(getClass().getResource("life.wav").toString());
        audio2 = new AudioClip(getClass().getResource("playerdead.wav").toString());
        audio3 = new AudioClip(getClass().getResource("armor.wav").toString());
        audio4 = new AudioClip(getClass().getResource("sound1.wav").toString());
        // missiles
        playerMissileImage = new Image(getClass().getResource("missile.png").toExternalForm());
    }

    /**
     * creates layer of current gamestate
     */
    private void createScoreLayer() {
        Player1Score.setFont(Font.font(null, FontWeight.BOLD, Settings.fontsSize));
        Player1Score.setFill(Color.BLACK);
        Player2Score.setFont(Font.font(null, FontWeight.BOLD, Settings.fontsSize));
        Player2Score.setFill(Color.BLACK);
        Level.setFont(Font.font(null, FontWeight.BOLD, Settings.fontsSize));
        Level.setFill(Color.BLACK);
        TotalEnemies.setFont(Font.font(null, FontWeight.BOLD, Settings.fontsSize));
        TotalEnemies.setTextFill(Color.BLACK);
        scoreLayer.getChildren().addAll(Player1Score, Player2Score, Level, TotalEnemies);
        Level.setText("Level" + "");
        double x = (Settings.SCENE_WIDTH);
        double y = (Level.getBoundsInLocal().getHeight());
        Level.relocate(x, y);
        Player1Score.setText("Player 1:" + "score1");
        y = (3 * Player1Score.getBoundsInLocal().getHeight());
        Player1Score.relocate(x, y);
        Player2Score.setText("Player 2:" + "score2");
        y = (5 * Player2Score.getBoundsInLocal().getHeight());
        Player2Score.relocate(x, y);
        //block for enemies count
        TotalEnemies.setText("Enemies To Win:" + String.valueOf((Settings.enemy - Settings.enemyCount)));
        y = (4 * TotalEnemies.getBoundsInLocal().getHeight()) / 2;
        TotalEnemies.relocate(x, y);
        Player1Score.setBoundsType(TextBoundsType.VISUAL);
        Player2Score.setBoundsType(TextBoundsType.VISUAL);
        Level.setBoundsType(TextBoundsType.VISUAL);
        TotalEnemies.setVisible(true);
    }

    /**
     * create players
     */
    private void createPlayers() {
        // player input
        if (Settings.player1Lives > 0 && !Settings.player1IsAlive) {
            Player player;
            Input input = new Input(scene);
            // register input listeners
            input.addListeners(); // TODO: remove listeners on game over
            Image image = player1Image;
            // center horizontally, position at 70% vertically
            double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
            double y = Settings.SCENE_HEIGHT * 0.7;
            // create player
            player = new Player(playfieldLayer, image, x, y, 180, 0, 0, 0, Settings.playerShipHealth, 0, Settings.playerShipSpeed, input);
            player.playerNumber = 1;
            // register player
            Settings.player1Lives--;
            Settings.player1IsAlive = true;
            players.add(player);
            audio4.play();
        }
        if (Settings.player2Lives > 0 && !Settings.player2IsAlive)
        {
            Player player2;
            Input input = new Input(scene);
            input.setupKey(KeyCode.W);
            input.setdownKey(KeyCode.S);
            input.setleftKey(KeyCode.A);
            input.setrightKey(KeyCode.D);
            input.setprimaryWeaponKey(KeyCode.SPACE);
            input.setsecondaryWeaponKeyKey(KeyCode.CONTROL);
            input.addListeners();
            Image image = player2Image;
            // center horizontally, position at 70% vertically
            double x = (Settings.SCENE_WIDTH / 2.0) + 40;
            double y = Settings.SCENE_HEIGHT * 0.7;
            // create player
            player2 = new Player(playfieldLayer, image, x + 20, y - 20, 0, 0, 0, 0, Settings.playerShipHealth, 0, Settings.playerShipSpeed, input);
            player2.playerNumber = 2;
            // register player
            Settings.player2Lives--;
            Settings.player2IsAlive = true;
            players.add(player2);
            audio4.play();
        }
    }

    /**
     * remove sprites from layer
     */
    private void removeSprites(List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iter = spriteList.iterator();
        while (iter.hasNext()) {
            SpriteBase sprite = iter.next();
            if (sprite.isRemovable()) {
                // remove from layer
                sprite.removeFromLayer();
                // remove from list
                iter.remove();
            }
        }
    }

    /**
     * remove sprites from layer
     */
    private void removeSprites(SpriteBase spriteList) {
        if (spriteList.isRemovable()) {
				try {
					this.finalize();
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
            // remove from layer
            spriteList.removeFromLayer();
            // remove from list
        }
    }

    /**
     * check collisions between players,enemies,bullets,missiles
     */
    private void checkCollisions() {
        //collisionEnemyBullets
        if (enemies.size() != 0) {
            for (Bullet bullet : bullets) {
                for (EnemyTank enemy : enemies) {
                    if (bullet.collidesWith(enemy)) {
                        collision = true;
                        enemy.getDamagedBy(bullet);
                        collision = false;
                        if (!enemy.isAlive()) {
                            enemy.setRemovable(true);
                            Settings.enemyCount--;
                            bullet.remove();
                        }
                    }
                }
            }
        }
        if (enemies.size() != 0) {
            for (Bullet player1bullet : player1bullets) {
                for (EnemyTank enemy : enemies) {
                    if (player1bullet.collidesWith(enemy)) {
                        collision = true;
                        enemy.getDamagedBy(player1bullet);
                        collision = false;
                        if (!enemy.isAlive()) {
                            enemy.setRemovable(true);
                            Settings.enemyCount--;
                            player1bullet.remove();
                        }
                    }
                }
            }
        }
        if (enemies.size() != 0) {
            for (Bullet player2bullet : player2bullets) {
                for (EnemyTank enemy : enemies) {
                    if (player2bullet.collidesWith(enemy)) {
                        collision = true;
                        enemy.getDamagedBy(player2bullet);
                        collision = false;
                        if (!enemy.isAlive()) {
                            enemy.setRemovable(true);
                            Settings.enemyCount--;
                            player2bullet.remove();
                            //continue;
                        }
                    }
                }
            }
        }
        for (Player player : players) {
            for (Bullet bullet : bullets) {
                if (bullet.collidesWith(player)) {
                    collision = true;
                    player.getDamagedBy(bullet);
                    collision = false;
                    bullet.remove();
                    audio3.play();
                    if (!player.isAlive()) {
                        player.setRemovable(true);
                        if (player.getPlayerNumber() == 1)
                            Settings.player1IsAlive = false;
                        else Settings.player2IsAlive = false;
                        audio2.play();
                    }
                }
            }
        }
        for (Bullet bullet : bullets) {
            for (Bullet bullet1 : bullets) {
                if (bullet == bullet1)
                    continue;
                if (bullet.collidesWith(bullet1)) {
                    bullet.remove();
                    bullet1.remove();
                }
            }
        }
        for (Bullet bullet : player1bullets) {
            for (Bullet bullet1 : player2bullets) {
                if (bullet.collidesWith(bullet1)) {
                    bullet.remove();
                    bullet1.remove();
                }
            }
        }
        for (Bullet bullet : player1bullets) {
            for (Bullet bullet1 : bullets) {
                if (bullet.collidesWith(bullet1)) {
                    bullet.remove();
                    bullet1.remove();
                }
            }
        }
        for (Bullet bullet : player2bullets) {
            for (Bullet bullet1 : bullets) {
                if (bullet.collidesWith(bullet1)) {
                    bullet.remove();
                    bullet1.remove();
                }
            }
        }
        for (Bullet bullet : player1bullets) {
            for (EnemyTank enemy : enemies) {
                if (bullet.collidesWith(enemy)) {
                    collision = true;
                    bullet.remove();
                    enemy.getDamagedBy(bullet);
                    collision = false;
                    if (!enemy.isAlive()) {
                        Settings.player1Score += 100;
                        Settings.enemyCount--;
                        enemy.setRemovable(true);
                    }
                }
            }
        }
        for (Bullet bullet : player2bullets) {
            for (EnemyTank enemy : enemies) {
                if (bullet.collidesWith(enemy)) {
                    collision = true;
                    bullet.remove();
                    enemy.getDamagedBy(bullet);
                    collision = false;
                    if (!enemy.isAlive()) {
                        Settings.player2Score += 100;
                        Settings.enemyCount--;
                        enemy.setRemovable(true);
                    }
                }
            }
        }
        // Movement Collision!
        for (Player player1 : players) {
            for (Player player2 : players) {
                if (player1 == player2)
                    continue;
                if (player1.collidesWith(player2)) {
                    int Rp1 = (int) player1.getDirection();
                    switch (Rp1) {
                        case 0: {
                            player1.canMoveUp = false;
                            player2.canMoveDown = false;
                        }
                        break;
                        case 180: {
                            player1.canMoveDown = false;
                            player2.canMoveUp = false;
                        }
                        break;
                        case 90: {
                            player1.canMoveRight = false;
                            player2.canMoveLeft = false;
                        }
                        break;
                        case -90: {
                            player1.canMoveLeft = false;
                            player2.canMoveRight = false;
                        }
                        break;
                    }
                }
                if (!player1.collidesWith(player2)) {
                    player1.canMoveUp = true;
                    player1.canMoveDown = true;
                    player1.canMoveLeft = true;
                    player1.canMoveRight = true;
                    player2.canMoveUp = true;
                    player2.canMoveDown = true;
                    player2.canMoveLeft = true;
                    player2.canMoveRight = true;
                    break;
                }
            }
        }
        for (EnemyTank enemy : enemies) {
            for (Player player : players) {
                if (player.collidesWith(enemy)) {
                    int direction = (int) player.getDirection();
                    switch (direction) {
                        case 0: {
                            player.canMoveUp = false;
                            enemy.canMoveDown = false;
                        }
                        break;
                        case 180: {
                            player.canMoveDown = false;
                            enemy.canMoveUp = false;
                        }
                        break;
                        case 90: {
                            player.canMoveRight = false;
                            enemy.canMoveLeft = false;
                        }
                        break;
                        case -90: {
                            player.canMoveLeft = false;
                            enemy.canMoveRight = false;
                        }
                        break;
                    }
                }
                if (!player.collidesWith(enemy)) {
                    player.canMoveUp = true;
                    player.canMoveDown = true;
                    player.canMoveLeft = true;
                    player.canMoveRight = true;
                    enemy.canMoveUp = true;
                    enemy.canMoveDown = true;
                    enemy.canMoveLeft = true;
                    enemy.canMoveRight = true;
                    break;
                }
            }
        }
        for (EnemyTank enemy : enemies) {
            for (Player player : players) {
                if (!enemy.collidesWith(player) /*&& (!player2.collidesWith(enemy)) &&(!enemy.collidesWith(enemy))*/) {
                    enemy.canMoveLeft = true;
                    enemy.canMoveUp = true;
                    enemy.canMoveDown = true;
                    enemy.canMoveRight = true;
                }
            }
        }
        for (EnemyTank enemy : enemies) {
            for (EnemyTank enemy1 : enemies) {
                if (enemy == enemy1)
                    continue;
                if (enemy.collidesWith(enemy1)) {
                    if (enemy == enemy1)
                        continue;
                    int direction = (int) enemy.getDirection();
                    switch (direction) {
                        case 0: {
                            enemy.canMoveUp = false;
                            enemy1.canMoveDown = false;
                        }
                        break;
                        case 180: {
                            enemy.canMoveDown = false;
                            enemy1.canMoveUp = false;
                        }
                        break;
                        case 90: {
                            enemy.canMoveRight = false;
                            enemy1.canMoveLeft = false;
                        }
                        break;
                        case -90: {
                            enemy.canMoveLeft = false;
                            enemy1.canMoveRight = false;
                        }
                        break;
                    }
                }
            }
        }
        // collision Missiles
        for (Missile missile : playerMissileList) {
            for (EnemyTank enemy : enemies) {
                if (missile.collidesWith(enemy)) {
                    collision = true;
                    enemy.getDamagedBy(missile);
                    missile.remove();
                    collision = false;
                }
                if (!enemy.isAlive())
                    enemy.setRemovable(true);
                Settings.enemyCount--;
            }
        }
    }

    /**
     * update players score
     */
    private void updateScore() {
        Player1Score.setText("PLAyer 1:" + String.valueOf(Settings.player1Lives) + "\n" + "Score:" + String.valueOf(Settings.player1Score));
        Player2Score.setText("Player 2:" + String.valueOf(Settings.player2Lives) + "\n" + "Score:" + String.valueOf(Settings.player2Score));
        TotalEnemies.setText("Enemies to Win:" + String.valueOf(Settings.enemy - Settings.enemyTotal + enemies.size()));
        for (Player player : players) {
            if (player.playerNumber == 1 && Settings.player1Score >= 1000) {
                Settings.player1Score = 0;
                audio1.play();
                Settings.player1Lives++;
            }
            if (player.playerNumber == 2 && Settings.player2Score >= 1000) {
                Settings.player2Score = 0;
                audio1.play();
                Settings.player2Lives++;
            }
        }
        Level.setText("Level:" + String.valueOf(0));
        for (int x = 0; x < players.size(); x++) {
            if (players.get(x).getPlayerNumber() == 2)
                break;
            Level.setText(String.valueOf(players.get(x).speed));
        }
    }

    /**
     * spawn enemies
     */
    private void spawnEnemies(boolean random) {

        if (random && randomValue.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }
        // image
        Image image = enemy1Image;
        if (Settings.enemyTotal != 0)
            image = enemy1Image;
        if (Settings.enemyTotal > 8 && Settings.enemyTotal < 16)
            image = enemy2Image;
        if (Settings.enemyTotal > 16)
            image = enemy3Image;

        // random speed
        double speed = randomValue.nextDouble() * 1.0 + 2.0;
        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = randomValue.nextDouble() * (Settings.SCENE_WIDTH - image.getWidth());
        double y = image.getHeight();
        x = image.getWidth();
        y = randomValue.nextDouble() * (Settings.SCENE_HEIGHT - image.getHeight());
        // create a sprite
        EnemyTank enemy = new EnemyTank(playfieldLayer, image, x, y, 0, 0, speed, 0, 1, Settings.playerShipHealth);
        // manage sprite
        Settings.enemyCount++;
        Settings.enemyTotal++;
        enemies.add(enemy);
//			}
//		}
    }

    /**
     * spawn bullets
     */
    private void spawnBullet(SpriteBase spriteList, List<Bullet> bulletlist) {

        Image image = bulletImage;
        double speed = Settings.playerMissileSpeed;
        // x position range: near player
        // y position: near player
        double x = spriteList.getCenterX();
        double y = spriteList.getCenterY() - 2;
        if (spriteList.getDirection() == 0) {
            x = spriteList.getCenterX() - 5;
            y = spriteList.getCenterY() - 21;
        }
        if (spriteList.getDirection() == 180) {
            x = spriteList.getCenterX() - 5;
            y = spriteList.getCenterY() + 12;
        }
        if (spriteList.getDirection() == 90) {
            x = spriteList.getCenterX() + 12;
            y = spriteList.getCenterY() - 5;
        }
        if (spriteList.getDirection() == -90) {
            x = spriteList.getCenterX() - 21;
            y = spriteList.getCenterY() - 5;
        }
        Bullet bullet = new Bullet(playfieldLayer, image, x, y, spriteList.getDirection(), 0, 0, speed, 1, Settings.playerMissileHealth);
        bulletlist.add(bullet);
    }

    /**
     * check for enemies on horizontals and verticals
     */
    private boolean noEnemiesOnLine(SpriteBase spriteList, List<EnemyTank> spriteBaseList) {
        //pauseGame();
        double view = spriteList.getDirection();
        boolean result = true;
        switch ((int) view) {
            //check up/down
            case 0: {
                double up = spriteList.getCenterX();
                for (SpriteBase enemy : spriteBaseList) {
                    if (spriteList == enemy)
                        continue;
                    if (spriteList.getCenterY() > enemy.getCenterY())
                        for (int range = -20; range < 20; range++) {
                            if ((int) up + range == (int) enemy.getCenterX()) {
                                result = false;
                                break;
                            }
                        }
                }
            }
            break;
            case 180: {
                double down = spriteList.getCenterX();
                for (SpriteBase enemy : spriteBaseList) {
                    if (spriteList == enemy)
                        continue;
                    if (spriteList.getCenterY() < enemy.getCenterY())
                        for (int range = -20; range < 20; range++) {
                            if ((int) down + range == (int) enemy.getCenterX()) {
                                result = false;
                                break;
                            }
                        }
                }
            }
            break;
            // TODO:check left/right
            case 90: {
                double right = spriteList.getCenterY();
                for (SpriteBase enemy : spriteBaseList) {
                    if (spriteList == enemy)
                        continue;
                    if (spriteList.getCenterX() < enemy.getCenterX())
                        for (int range = -20; range < 20; range++) {
                            if ((int) right + range == (int) enemy.getCenterY()) {
                                result = false;
                                break;
                            }
                        }
                }
            }
            break;
            case -90: {
                double left = spriteList.getCenterY();
                for (SpriteBase enemy : spriteBaseList) {
                    if (spriteList == enemy)
                        continue;
                    if (spriteList.getCenterX() > enemy.getCenterX())
                        for (int range = -20; range < 20; range++) {
                            if ((int) left + range == (int) enemy.getCenterY()) {
                                result = false;
                                break;
                            }
                        }
                }
            }
            break;
        }
        //resumeGame();
        return result;
    }

    /**
     * check for players on horizontals and verticals
     */
    private boolean playersOnLine(SpriteBase enemy, Player player1) {
        //pauseGame();
        double view = enemy.getDirection();
        boolean result = true;
        switch ((int) view) {
            //check up/down
            case 0: {
                double up = enemy.getCenterX();
                if (enemy.getCenterY() > player1.getCenterY())
                    for (int range = -20; range < 20; range++) {
                        if ((int) up + range == (int) player1.getCenterX()) {
                            result = false;
                            break;
                        }
                    }
            }
            break;
            case 180: {
                double up = enemy.getCenterX();
                if (enemy.getCenterY() < player1.getCenterY())
                    for (int range = -20; range < 20; range++) {
                        if ((int) up + range == (int) player1.getCenterX()) {
                            result = false;
                            break;
                        }
                    }
            }
            break;
            case 90: {
                double up = enemy.getCenterY();
                if (enemy.getCenterX() < player1.getCenterX())
                    for (int range = -20; range < 20; range++) {
                        if ((int) up + range == (int) player1.getCenterY()) {
                            result = false;
                            break;
                        }
                    }
            }
            break;
            case -90: {
                double up = enemy.getCenterY();
                if (enemy.getCenterX() > player1.getCenterX())
                    for (int range = -20; range < 20; range++) {
                        if ((int) up + range == (int) player1.getCenterY()) {
                            result = false;
                            break;
                        }
                    }
            }
            break;
        }
        return result;
    }

    /**
     * stop Game
     */
    private void stopGame() {
        clearGame();
    }
}