package pong;

public class Level {

    public static World createLevel(World world, Engine engine, int level)
    {

        double height = engine.getWorldHeight();
        double width = engine.getWorldWidth();

        switch (level)
        {
            case 1:
                Segment segment0 = new Segment(new Vector2D(width / 2, (height * 1) / 12), new Vector2D(width / 2, (height * 3) / 12));
                world.addStaticCollision(segment0);
                new SegmentRender(engine, segment0);

                Segment segment1 = new Segment(new Vector2D(width / 2, (height * 5) / 12), new Vector2D(width / 2, (height * 7) / 12));
                world.addStaticCollision(segment1);
                new SegmentRender(engine, segment1);

                Segment segment2 = new Segment(new Vector2D(width / 2, (height * 9) / 12), new Vector2D(width / 2, (height * 11) / 12));
                world.addStaticCollision(segment2);
                new SegmentRender(engine, segment2);
                break;

            case 2:

        }

        return world;

    };

}
