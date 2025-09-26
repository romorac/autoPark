import { Route } from '@angular/router';
import { GenLetraPaso1Component } from 'app/entities/letra/gen-letra-paso1/gen-letra-paso1.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: GenLetraPaso1Component,
  data: {
    pageTitle: 'AUTOPARK',
  },
};
