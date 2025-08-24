@import org.example.hexlet.dto.users.UserPage
@param UserPage page

@template.layout.page(
    content = @`
        <h1>User Profile: ${page.getUser().getName()}</h1>
        <p>Email: ${page.getUser().getEmail()}</p>

        <div class="action-buttons">
            <a href="/users/${page.getUser().getId()}/edit" class="btn btn-primary">Edit Profile</a>
            <form action="/users/${page.getUser().getId()}/delete" method="post" style="display:inline">
                <button type="submit" class="btn btn-danger"
                        onclick="return confirm('Are you sure you want to delete this account?')">
                    Delete Account
                </button>
            </form>
        </div>

        <p><a href="/users" class="btn btn-secondary">Back to all users</a></p>
    `,
    usersPage = null,
    coursesPage = null,
    page = page
)
