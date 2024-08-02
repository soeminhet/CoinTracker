import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smh.design.R
import com.smh.design.extension.noRippleClick
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview

@Composable
fun CircleButton(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    size: Dp = 50.dp,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(
                elevation = 6.dp,
                shape = CircleShape,
                ambientColor = MaterialTheme.colorScheme.inversePrimary,
                spotColor = MaterialTheme.colorScheme.inversePrimary
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = CircleShape
            )
            .clip(CircleShape)
            .noRippleClick(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
        )
    }
}

@DayPreview
@Composable
private fun CircleButtonPreview() {
    CoinTrackerTheme {
        Surface {
            CircleButton(
                icon = R.drawable.ic_add,
                onClick = {}
            )
        }
    }
}

@NightPreview
@Composable
private fun CircleButtonNightPreview() {
    CoinTrackerTheme {
        Surface {
            CircleButton(
                icon = R.drawable.ic_add,
                onClick = {}
            )
        }
    }
}