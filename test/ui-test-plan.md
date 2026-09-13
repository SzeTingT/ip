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

**Aim:** Verify that task-content, task-number, and unknown-command exceptions report their specific messages without creating tasks.

**Inputs:**
```text
todo
deadline return book
deadline return book /by
event project meeting /from Mon 2pm
event project meeting /to 4pm
mark
mark 1
mark abc
unknown
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

> Invalid task details.
> Invalid task details.
> Invalid task details.
> Invalid task details.
> Invalid task details.
> Invalid task index.
> Invalid task index.
> Invalid task index.
> Unrecognised command.
> ____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Delete tasks

**Aim:** Verify that `delete` removes the selected task, renumbers the remaining tasks, and rejects an out-of-range task number without changing the list.

**Inputs:**
```text
todo first task
deadline second task /by Friday
event project meeting /from Aug 6th 2pm /to 4pm
delete 3
list
delete 3
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

> Added: first task
> Added: second task (by: Friday)
> Added: project meeting (from: Aug 6th 2pm, to: 4pm)
> Noted. I've removed this task:
  [E][ ] project meeting (from: Aug 6th 2pm, to: 4pm)
Now you have 2 tasks in the list.
> 1.[T][ ] first task
2.[D][ ] second task (by: Friday)
> Invalid task index.
> 1.[T][ ] first task
2.[D][ ] second task (by: Friday)
> ____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Open the help guide

**Aim:** Verify that the `help` command displays the command guide without changing the task list.

**Inputs:**
```text
help
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
Hello! I'm Labubu. A task tracker bot.

Tasks available: 
To-do: todo [task-title] 
Deadline: deadline [task-title] /by [date-time] 
Event: event [task-title] /from [date-time] /to [date-time] 
Help: help
Enter dates in the following format: dd/MM/yyyy <optional>HH:mm</optional>  e.g: 06/07/2026 18:30

> Hello! I'm Labubu, your personal task-tracking bot.

Here are the commands you can use:

ADD TASKS
  todo [task title]
  Adds a task without a date or time.

  deadline [task title] /by [date and time]
  Adds a task that must be completed by a specified date and time.

  event [task title] /from [start date and time] /to [end date and time]
  Adds a task that takes place during a specified period.

Dates should use this format:
  dd/MM/yyyy
  dd/MM/yyyy HH:mm

Examples:
  todo read book
  deadline return book /by 06/07/2026
  event team meeting /from 06/07/2026 14:00 /to 06/07/2026 15:00

MANAGE TASKS
  list
  Displays all tasks.

  find [keyword]
  Displays tasks whose titles contain the keyword.

  mark [task number]
  Marks a task as completed.

  unmark [task number]
  Marks a task as incomplete.

  delete [task number]
  Deletes a task.

OTHER
  help
  Opens this help guide.

  bye
  exit
  quit
  Saves your tasks and closes Labubu.

Task status indicators:
  [ ] Incomplete
  [-] In progress
  [X] Completed

Task numbers are based on their position in the task list.
> ____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
