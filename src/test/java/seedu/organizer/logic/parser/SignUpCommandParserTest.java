package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.organizer.logic.commands.SignUpCommand;
import seedu.organizer.model.user.User;

//@@author dominickenn
public class SignUpCommandParserTest {
    private SignUpCommandParser parser = new SignUpCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        User expectedUser = new User("bobby", "bobby");

        assertParseSuccess(parser, " u/bobby p/bobby", new SignUpCommand(expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, " bobby p/b0bby", expectedMessage);

        // missing password prefix
        assertParseFailure(parser, " u/bobby b0bby", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " bobby b0bby", expectedMessage);

        // missing username
        assertParseFailure(parser, "u/ p/b0bby", expectedMessage);

        // missing password
        assertParseFailure(parser, "u/bobby p/ ", expectedMessage);

        // missing fields
        assertParseFailure(parser, "u/ p/ ", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username : contains special character
        assertParseFailure(parser, " u/b@bby p/bobby", User.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password : contains special character
        assertParseFailure(parser, " u/bobby p/b@bby", User.MESSAGE_PASSWORD_CONSTRAINTS);

        // invalid username : length < 5
        assertParseFailure(parser, " u/bobb p/bobby", User.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password : length < 5
        assertParseFailure(parser, " u/bobby p/bobb", User.MESSAGE_PASSWORD_CONSTRAINTS);

        // invalid username : blank
        assertParseFailure(parser, " u/ p/bobby", User.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password : blank
        assertParseFailure(parser, " u/bobby p/ ", User.MESSAGE_PASSWORD_CONSTRAINTS);
    }
}
