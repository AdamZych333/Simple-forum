import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'layout-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FooterComponent {
  today: number = Date.now();
}
