# UI Test Plan

This plan tests the `Labubu` console application. Each test starts a fresh application session.

## Test case: Create and list task types

**Aim:** Verify that task commands create the correct task subclasses and that `list` shows markers, completion states, and subtype fields.

**Inputs:**
```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
mark 2
list
bye
```

**Expected output:**
```text
____________________________________________________________
██╗      █████╗ ██████╗ ██╗   ██╗██████╗ ██╗   ██╗
██║     ██╔══██╗██╔══██╗██║   ██║██╔══██╗██║   ██║
██║     ███████║██████╔╝██║   ██║██████╔╝██║   ██║
██║     ██╔══██║██╔══██╗██║   ██║██╔══██╗██║   ██║
███████╗██║  ██║██████╔╝╚██████╔╝██████╔╝╚██████╔╝
╚══════╝╚═╝  ╚═╝╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ 
Hello! I'm Labubu.
What can I do for you?

> Added: borrow book
> Added: return book (by: Sunday)
> Added: project meeting (from: Mon 2pm, to: 4pm)
> > 1.[T][ ] borrow book
2.[D][X] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm, to: 4pm)
> ____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Reject incomplete task fields

**Aim:** Verify that blank descriptions and missing deadline or event fields do not create tasks.

**Inputs:**
```text
todo
deadline return book
deadline return book /by
event project meeting /from Mon 2pm
event project meeting /to 4pm
bye
```

**Expected output:**
```text
____________________________________________________________
██╗      █████╗ ██████╗ ██╗   ██╗██████╗ ██╗   ██╗
██║     ██╔══██╗██╔══██╗██║   ██║██╔══██╗██║   ██║
██║     ███████║██████╔╝██║   ██║██████╔╝██║   ██║
██║     ██╔══██║██╔══██╗██║   ██║██╔══██╗██║   ██║
███████╗██║  ██║██████╔╝╚██████╔╝██████╔╝╚██████╔╝
╚══════╝╚═╝  ╚═╝╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ 
Hello! I'm Labubu.
What can I do for you?

> Invalid input.
> Invalid input.
> Invalid input.
> Invalid input.
> Invalid input.
> ____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
```
