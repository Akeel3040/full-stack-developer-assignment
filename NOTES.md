# Patch notes

I focused on correctness and usability across the stack. On the frontend, search now waits briefly after typing, cancels obsolete requests, resets errors reliably, and returns to page one when filters change. I also added accessible labels, announced errors, and mobile table scrolling.

On the backend, the search query now groups title/description matches before applying archived and status filters. Before this, SQL operator precedence allowed title matches to bypass those constraints. I removed an artificial request delay, validate page, page-size and status input with useful 400 responses, prevent pagination arithmetic overflow, and replaced standard output with structured logging. The equivalent H2 reference query and Oracle artifact now match the fixed logic. Integration tests cover the predicate and input-validation contract.

I did not redesign the API or implement database-level pagination; those would broaden this focused patch. The biggest remaining risk is search performance as data grows: contains searches and loading all matching rows before pagination will need a data-access redesign and indexing strategy.

I used Codex to inspect the existing code, identify the frontend request and pagination issues, and implement this focused patch.
