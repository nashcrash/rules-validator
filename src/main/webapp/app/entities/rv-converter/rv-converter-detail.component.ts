import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRvConverter } from 'app/shared/model/rv-converter.model';

@Component({
  selector: 'jhi-rv-converter-detail',
  templateUrl: './rv-converter-detail.component.html',
})
export class RvConverterDetailComponent implements OnInit {
  rvConverter: IRvConverter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rvConverter }) => (this.rvConverter = rvConverter));
  }

  previousState(): void {
    window.history.back();
  }
}
