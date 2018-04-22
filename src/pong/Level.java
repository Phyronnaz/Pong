package pong;

import javax.management.RuntimeErrorException;

public class Level {

    public static void createLevel(World world, Engine engine, int level) throws RuntimeErrorException {

        double height = engine.getWorldHeight();
        double width = engine.getWorldWidth();

        double haflWidth = width / 2;
        double haflHeight = height / 2;

        switch (level)
        {
            case 0:
                break;

            case 1: // Not harcoded! ;)
                Segment segment1_0 = new Segment(new Vector2D(haflWidth, (height * 1) / 12), new Vector2D(haflWidth, (height * 3) / 12));
                world.addStaticCollision(segment1_0);
                new SegmentRender(engine, segment1_0);

                Segment segment1_1 = new Segment(new Vector2D(haflWidth, (height * 5) / 12), new Vector2D(haflWidth, (height * 7) / 12));
                world.addStaticCollision(segment1_1);
                new SegmentRender(engine, segment1_1);

                Segment segment1_2 = new Segment(new Vector2D(haflWidth, (height * 9) / 12), new Vector2D(haflWidth, (height * 11) / 12));
                world.addStaticCollision(segment1_2);
                new SegmentRender(engine, segment1_2);
                break;

            case 2: // Kinda hardcoded...

                double offSet = 100;

                Segment segment2_0 = new Segment(new Vector2D(haflWidth - 50, haflHeight - offSet),new Vector2D(haflWidth, haflHeight - 50 - offSet));
                Segment segment2_1 = new Segment(new Vector2D(haflWidth, haflHeight - 50 - offSet),new Vector2D(haflWidth + 50, haflHeight - offSet));
                Segment segment2_2 = new Segment(new Vector2D(haflWidth + 50, haflHeight - offSet),new Vector2D(haflWidth, haflHeight + 50 - offSet));
                Segment segment2_3 = new Segment(new Vector2D(haflWidth, haflHeight + 50 - offSet),new Vector2D(haflWidth - 50, haflHeight - offSet));

                world.addStaticCollision(segment2_0);
                world.addStaticCollision(segment2_1);
                world.addStaticCollision(segment2_2);
                world.addStaticCollision(segment2_3);

                new SegmentRender(engine, segment2_0);
                new SegmentRender(engine, segment2_1);
                new SegmentRender(engine, segment2_2);
                new SegmentRender(engine, segment2_3);

                Segment segment2_4 = new Segment(new Vector2D(haflWidth - 50, haflHeight + offSet),new Vector2D(haflWidth, haflHeight - 50 + offSet));
                Segment segment2_5 = new Segment(new Vector2D(haflWidth, haflHeight - 50 + offSet),new Vector2D(haflWidth + 50, haflHeight + offSet));
                Segment segment2_6 = new Segment(new Vector2D(haflWidth + 50, haflHeight + offSet),new Vector2D(haflWidth, haflHeight + 50 + offSet));
                Segment segment2_7 = new Segment(new Vector2D(haflWidth, haflHeight + 50 + offSet),new Vector2D(haflWidth - 50, haflHeight + offSet));

                world.addStaticCollision(segment2_4);
                world.addStaticCollision(segment2_5);
                world.addStaticCollision(segment2_6);
                world.addStaticCollision(segment2_7);

                new SegmentRender(engine, segment2_4);
                new SegmentRender(engine, segment2_5);
                new SegmentRender(engine, segment2_6);
                new SegmentRender(engine, segment2_7);


        }

    };

}
