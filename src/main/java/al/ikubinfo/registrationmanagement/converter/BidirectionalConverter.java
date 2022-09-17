package al.ikubinfo.registrationmanagement.converter;

public interface BidirectionalConverter<D, E> {
    D toDto(E entity);

    E toEntity(D dto);

}
