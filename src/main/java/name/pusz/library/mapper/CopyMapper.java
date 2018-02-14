package name.pusz.library.mapper;

import name.pusz.library.controller.exception.BookNotFoundException;
import name.pusz.library.domain.Copy;
import name.pusz.library.domain.dto.incoming.CopyDtoIn;
import name.pusz.library.domain.dto.outgoing.CopyDtoOut;
import name.pusz.library.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CopyMapper {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private DbService dbService;

    public CopyDtoOut mapToCopyDtoOut(Copy copy) {
        return new CopyDtoOut(
                copy.getId(),
                bookMapper.mapToBookDtoOut(copy.getBook()),
                copy.getStatus());
    }

    public List<CopyDtoOut> mapToCopyDtoOutList(List<Copy> copies) {
        return copies.stream().map(c -> mapToCopyDtoOut(c)).collect(Collectors.toList());
    }

    public Copy mapToCopy(CopyDtoIn copyDtoIn) throws BookNotFoundException {
        return new Copy(
                copyDtoIn.getId(),
                dbService.findBookWithId(copyDtoIn.getBookId()),
                copyDtoIn.getStatus(),
                null);
    }
}
