import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConverter } from 'app/shared/model/converter.model';

@Component({
  selector: 'jhi-converter-detail',
  templateUrl: './converter-detail.component.html',
})
export class ConverterDetailComponent implements OnInit {
  converter: IConverter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ converter }) => (this.converter = converter));
  }

  previousState(): void {
    window.history.back();
  }
}
