@import org.example.hexlet.dto.users.UsersPage
@param UsersPage page

@template.layout.page(
    content = @`
        <h1>${page.getHeader() != null ? page.getHeader() : "All Users"}</h1>

        @if(page.getTerm() != null && !page.getTerm().isEmpty())
            <p>Search results for: "${page.getTerm()}"</p>
        @endif

        @if(page.getUsers().isEmpty())
            <p>No users found</p>
        @else
            @for(var user : page.getUsers())
                <a href="/users/${user.getId()}">${user.getName()}</a>
            @endfor
        @endif
        <p><a href="/users/build">Register New User</a></p>
    `,
    usersPage = page,
    coursesPage = null
)