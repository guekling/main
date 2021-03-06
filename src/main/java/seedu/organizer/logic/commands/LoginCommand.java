package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

//@@author dominickenn
/**
 * Login a user to PrioriTask.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "in";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login to PrioriTask. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "david "
            + PREFIX_PASSWORD + "david1234 ";

    public static final String MESSAGE_SUCCESS = "User log in successful : %1$s";
    public static final String MESSAGE_USER_NOT_FOUND = "This user does not exist";
    public static final String MESSAGE_CURRENTLY_LOGGED_IN = "A user is currently logged in";
    public static final String MESSAGE_WRONG_PASSWORD = "Wrong password";

    private final User toLogin;

    /**
     * Creates a LoginCommand
     * to login the specified {@code User}
     */
    public LoginCommand(User user) {
        requireNonNull(user);
        toLogin = user;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(toLogin, model, history);
        try {
            model.loginUser(toLogin);
            history.clear();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toLogin));
        } catch (UserNotFoundException unf) {
            throw new CommandException(String.format(MESSAGE_USER_NOT_FOUND, toLogin));
        } catch (UserPasswordWrongException upw) {
            throw new CommandException(MESSAGE_WRONG_PASSWORD);
        } catch (CurrentlyLoggedInException cli) {
            throw new CommandException(MESSAGE_CURRENTLY_LOGGED_IN);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toLogin.equals(((LoginCommand) other).toLogin));
    }
}

