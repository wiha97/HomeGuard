package JWutil;

public class Validate {
    private static final String RETURN = "<bq";

    public static int number() {
        return validateNumber(": ", 0);
    }
    public static int number(int min) {
        return validateNumber(": ", min);
    }

    public static int number(String prompt) {
        return validateNumber(prompt, 0);
    }

    public static int number(String prompt, int min) {
        return validateNumber(prompt, min);
    }

    private static int validateNumber(String prompt, int min) {
        while (true) {
            Print.same(prompt);
            String input = Print.newScan();
            if (isNumber(input)) {
                int num = Integer.parseInt(input);
                if (num >= min)
                    return num;
                Print.line(Print.alert("Must be above " + min));
            } else
                Print.line(Print.alert("Must contain number!"));
        }
    }

    public static String option(String options) {
        String opt = "";
        options += RETURN;
        boolean isValid = false;
        do {
            Print.same("Select: ");
            opt = Print.newScan();
            if (!opt.isEmpty())
                isValid = options.contains(opt);
            if (!isValid)
                Print.line(Print.alert(String.format("Must be a valid option: %s", prettifyOptions(options).toUpperCase())));
        } while (!isValid);
        return opt.replace('<', 'q').replace('b', 'q');
    }

    public static String optString(String[] arr) {
        String opt = "";
        boolean isValid = false;
        do {
            Print.same("Enter: " + Print.NAVY);
            opt = Print.newScan();
            Print.same(Print.RESET);
            for (String word : arr) {
                if (word.startsWith(opt)) {
                    isValid = true;
                    opt = word;
                    break;
                }
            }
            if (!isValid) {
                Print.line(Print.alert("Invalid command: " + opt));
                Print.same(Print.alert("Must be any of: {"));
                int i = 0;
                for(String word : arr)
                    Print.same(word + (++i < arr.length ? ", ":""));
                Print.line(Print.alert("}"));
            }
        } while (!isValid);
        return opt.toLowerCase();
    }

    private static String prettifyOptions(String opts) {
        String str = "";
        for (char opt : opts.toCharArray()) {
            str += String.format("[%s] ", opt);
        }
        return str;
    }

    private static boolean isNumber(String input) {
        if (!input.isEmpty())
            return input.chars().allMatch(Character::isDigit);
        return false;
    }
}
