package knbit.events.bc;

import com.google.common.collect.ImmutableList;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by novy on 29.03.15.
 */


@RestController
@Api(value = "foo_endpoint")
public class SwaggerDummyEndpoint {

    @RequestMapping(value = "/foo", method = RequestMethod.GET)
    @ApiOperation(value = "This method returns all foos")
    public Collection<Foo> allFoos() {
        return ImmutableList.of(
                new Foo("1", "foo1", "bar1"),
                new Foo("2", "foo2", "bar2")
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/foo", method = RequestMethod.POST)
    @ApiOperation(value = "This method adds foo")
    public void addFoo(Foo foo) {
        System.out.println(foo);
    }

    @RequestMapping(value = "/foo/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Method to update foo")
    public void updateFoo(@PathVariable(value = "id") String id, Foo foo) {
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/foo/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "This endpoint should be used to delete foo")
    public void deleteFoo(@PathVariable(value = "id") String id) {
    }

    @Value
    private class Foo {

        private final String id;
        private final String foo;
        private final String bar;

    }

}