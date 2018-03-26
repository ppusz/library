package name.pusz.library.utils;

import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.Optional;

@Component
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return Optional.ofNullable(localDate)
                .map(d -> Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .orElse(null);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return Optional.ofNullable(date)
                .map(d -> d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .orElse(null);
    }
}
