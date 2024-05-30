package androidtown.org.converter;

public interface Converter<T, R> {

    R convert(T t);
}
