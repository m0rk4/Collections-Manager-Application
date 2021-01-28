package by.mark.mangareviewer.domain;

public final class Views {
    public interface Id {}

    public interface IdText extends Id {}

    public interface FullProfile extends IdText {}

    public interface FullCollection extends IdText {}

    public interface FullComment extends IdText {}

    public interface FullItem extends IdText {}
}