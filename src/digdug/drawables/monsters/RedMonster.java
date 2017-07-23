package digdug.drawables.monsters;

import digdug.GameManager;

public class RedMonster extends Monster {

    public RedMonster(GameManager game, int i, int j) {
        super(game, game.getImages().getRedMonsterRight()[0], game.getImages().getRedMonsterKill(), i, j);
        setMoveAnimations(game.getImages().getRedMonsterUp(), game.getImages().getRedMonsterDown(),
                game.getImages().getRedMonsterLeft(), game.getImages().getRedMonsterRight());
        setGhostAnimation(game.getImages().getRedMonsterGhost());
        setDieAnimation(game.getImages().getRedMonsterKill());
    }

}
