@import org.example.hexlet.dto.users.UserPage
@param UserPage page

@template.layout.page(
    content = @`
        <h1>User Profile: ${page.getUser().getName()}</h1>
        <p>Email: ${page.getUser().getEmail()}</p>

        <div>
            <a href="/users/${page.getUser().getId()}/edit">Edit Profile</a>
            <form action="/users/${page.getUser().getId()}" method="post" style="display:inline">
                <input type="hidden" name="_method" value="DELETE">
                <button type="submit">Delete Account</button>
            </form>
        </div>

        <p><a href="/users">Back to all users</a></p>
    `
)
